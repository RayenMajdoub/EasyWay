package com.example.easyway.View

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import com.example.easyway.BroadcastReceiver.AlarmBroadcastReceiver
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.preference.PreferenceManager
import com.example.easyway.BroadcastReceiver.AlarmService
import com.example.easyway.R
import com.example.easyway.Repository.SessionManager
import com.example.easyway.Utils.LocationPermissionHelper
import com.example.easyway.Utils.NotificationPermissionHelper
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.api.directions.v5.models.Bearing
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.Plugin.Mapbox
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.applyLanguageAndVoiceUnitOptions
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.base.route.NavigationRouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.lifecycle.MapboxNavigationApp
import com.mapbox.navigation.core.lifecycle.requireMapboxNavigation
import com.mapbox.navigation.core.trip.session.RouteProgressObserver
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import com.mapbox.search.*
import com.mapbox.search.common.AsyncOperationTask
import com.mapbox.search.result.SearchResult
import java.lang.ref.WeakReference

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class Home : AppCompatActivity()/*, OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener */, OnMapClickListener {

    private val mapboxNavigation: MapboxNavigation by requireMapboxNavigation(

        onInitialize = this::initNavigation
    )
    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private var myLiveLocation: MutableLiveData<Point> = MutableLiveData()
    private var myLiveBearing: MutableLiveData<Double> = MutableLiveData()
    private lateinit var routeLineOptions: MapboxRouteLineOptions
    private lateinit var routeLineApi: MapboxRouteLineApi
    private lateinit var routeLineView: MapboxRouteLineView
    private lateinit var searchEngine: SearchEngine
    private lateinit var searchRequestTask: AsyncOperationTask
    private lateinit var searchRequestTask2: AsyncOperationTask

    private val searchCallback = object : SearchCallback {

        override fun onResults(results: List<SearchResult>, responseInfo: ResponseInfo) {
            if (results.isEmpty()) {
                Log.i("SearchApiExample", "No reverse geocoding results")
            } else {
                Log.i("SearchApiExample", "Reverse geocoding results: $results")
                if (results[0].address!!.locality != null) {
                    OtherMarkerLocation.value = results[0].address!!.locality!!
                } else if(results[0].address!!.region != null){
                    OtherMarkerLocation.value = results[0].address!!.region!!
                }else
                {
                    OtherMarkerLocation.value = "Un named place !"

                }
            }
        }

        override fun onError(e: Exception) {
            Log.i("SearchApiExample", "Reverse geocoding error", e)
        }
    }
    private val searchCallback2 = object : SearchCallback {

        override fun onResults(results: List<SearchResult>, responseInfo: ResponseInfo) {
            if (results.isEmpty()) {
                Log.i("SearchApiExample", "No reverse geocoding results")
            } else {
                Log.i("SearchApiExample", "Reverse geocoding results: $results")
                if (results[0].address!!.locality != null) {
                    myLocationAdresse.value = results[0].address!!.locality!!
                } else {
                    myLocationAdresse.value = results[0].address!!.region!!
                }
            }
        }

        override fun onError(e: Exception) {
            Log.i("SearchApiExample", "Reverse geocoding error", e)
        }
    }
    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val routeProgressObserver = RouteProgressObserver { routeProgress ->
        routeLineApi.updateWithRouteProgress(routeProgress) { result ->
            routeLineView.renderRouteLineUpdate(mapView!!.getMapboxMap().getStyle()!!, result)
        }
    }
    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
        myLiveBearing.value = it
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
        myLiveLocation.value = it

    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {

            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    //localisation actuelle et permission END
    private lateinit var map: Mapbox
    private var gsc: GoogleSignInClient? = null

    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mapView = findViewById(R.id.mapView)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val notif = prefs.getBoolean("notifications_header", true)
        if (notif) {
            val AlarmService = Intent(this, AlarmService::class.java)
            startService(AlarmService)
        }
        // Sets up permissions request launcher.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val receiver = ComponentName(this, AlarmBroadcastReceiver::class.java)
        val pm = packageManager
        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        val notificationPermissionHelper: NotificationPermissionHelper =
            NotificationPermissionHelper()
        notificationPermissionHelper.checkNotificationPermission(this)
        locationPermissionHelper.checkPermissions {
            onMapReady()
            routeLineOptions = MapboxRouteLineOptions.Builder(this)
                .displayRestrictedRoadSections(true)
                .styleInactiveRouteLegsIndependently(true)
                .displayRestrictedRoadSections(true)
                .displaySoftGradientForTraffic(true)
                .softGradientTransition(30).build()
            routeLineApi = MapboxRouteLineApi(routeLineOptions)
            routeLineView = MapboxRouteLineView(routeLineOptions)
            searchEngine = SearchEngine.createSearchEngineWithBuiltInDataProviders(
                SearchEngineSettings(getString(R.string.mapbox_access_token))
            )
            mapView.getMapboxMap().addOnMapLongClickListener {
                onMapClick(it)

            }


        }

        val list_fragment = Button_lists_sheet()

//search history
        val floating_menu_button: FloatingActionButton = findViewById(R.id.floating_action_button)
        val floating_nav_button: FloatingActionButton = findViewById(R.id.floating_navigate)
        val navigationView: NavigationView = findViewById(R.id.navigationview)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        floating_menu_button.setOnClickListener {
            drawerLayout.open()
        }
        floating_nav_button.setOnClickListener {
            val intent = Intent(this, TurnByTurnExperienceActivity::class.java)

            startActivity(intent)
        }


        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.General -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
                R.id.Saved_places -> {
                    val intent = Intent(this, saved_places::class.java)
                    startActivity(intent)
                }
                R.id.tickets -> {
                    val intent = Intent(this, MyTickets::class.java)
                    startActivity(intent)
                }
                R.id.logout->{
                        SessionManager.clearData(this)
                        gsc?.signOut()
                        val intent = Intent(this, Login::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(intent)

                }
                R.id.about->{
                    MaterialAlertDialogBuilder(this)
                        .setTitle("About EasyWay")
                        .setMessage("EasyWay is a platform that allows you to consult your preferred means of transportation\n" +
                                "and find the best route to your destination directly from the homepage. Even the lost can find the solution easily since it gives you" +
                                " the cheapest and shortest subway, bus or cab line needed, even if you only enter a street name or\n" +
                                "or a neighborhood thanks to its geolocation feature.\n" +
                                "also allows travelers to plan and pay for their trips with any public transportation\n" +
                                "It also allows travelers to plan and pay for their trips with any public transport, shared transport or micro-mobility operator.\n" +
                                "\n" +""
                          )
                        .setNegativeButton("Close") { dialog, which ->
                            dialog.dismiss()
                        }
                .show()
                }
                else -> Log.w("SettingsLog", "hello")
            }
            true

        }
        BottomSheetBehavior.from(findViewById(R.id.sheet)).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, list_fragment)
            commit()
        }
    }

    private fun updateCamera(location: Location) {
        val mapAnimationOptions = MapAnimationOptions.Builder().duration(1500L).build()
        mapView.camera.easeTo(
            CameraOptions.Builder()
                // Centers the camera to the lng/lat specified.
                .center(Point.fromLngLat(location.longitude, location.latitude))
                // specifies the zoom value. Increase or decrease to zoom in or zoom out
                .zoom(12.0)
                // specify frame of reference from the center.
                .padding(EdgeInsets(500.0, 0.0, 0.0, 0.0)).build(), mapAnimationOptions
        )
    }

    companion object {
        var OtherMarkerLocation: MutableLiveData<String> = MutableLiveData("")
        var myLocationAdresse: MutableLiveData<String> = MutableLiveData("")
         var myLiveDestination: MutableLiveData<Point> = MutableLiveData()

    }

    //Map LifeCycle functions
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        mapView.location.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location.removeOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        mapView.gestures.removeOnMoveListener(onMoveListener)
        routeLineApi.cancel()
        routeLineView.cancel()
        //mapboxNavigation.onDestroy()
        //searchRequestTask.cancel()
        //searchRequestTask2.cancel()

    }

    //Map LifeCycle END
//MapReady
    private fun onMapReady() {

        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder().zoom(14.0).build()
        )
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val mapskin = prefs.getBoolean("map_header", false)
        if (!mapskin) {
            mapView.getMapboxMap().loadStyleUri(
                Style.LIGHT
            ) {
                initLocationComponent()
                setupGesturesListener()
            }
        }else{
            mapView.getMapboxMap().loadStyleUri(
                Style.DARK
            ) {
                initLocationComponent()
                setupGesturesListener()
            }
        }

    }

    //MapReadyEND
    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    //INIT LOCATION AND PUCK
    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@Home,
                    R.drawable.ic_my_location,
                ), shadowImage = AppCompatResources.getDrawable(
                    this@Home,
                    R.drawable.ic_my_location,
                ), scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    //INIT LOCATION END
    private fun onCameraTrackingDismissed() {
      //  Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location.removeOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        mapView.location.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //ADD MARKER
    private fun addAnnotationToMap(point: Point) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@Home, R.drawable.ic_marker
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager(mapView)
// Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.
                .withPoint(point)
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                .withIconImage(it)
// Add the resulting pointAnnotation to the map.
            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }

    //ADD MARKER END
//BIT MAP CONVERTER
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    override fun onMapClick(point: Point): Boolean {
        val annotationApi = mapView.annotations
        annotationApi.cleanup()
        addAnnotationToMap(point)
        Log.w("Point on click" , point.toString())
        myLiveDestination.value = point
        Log.w("Point on click" ,myLiveDestination.value.toString())

        myLiveLocation.observe(this) {
            getRoute(it, myLiveDestination.value!!)
            findAddress(point)

        }

        //    OtherMarkerLocation.value=getAddress(point)
        return true
    }

    private fun initNavigation() {

        if (!MapboxNavigationApp.isSetup()) {
            MapboxNavigationApp.setup {
                NavigationOptions.Builder(this)
                    .accessToken(getString(R.string.mapbox_access_token))
                    .build()
            }
        }

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                MapboxNavigationApp.attach(owner)
            }

            override fun onPause(owner: LifecycleOwner) {
                MapboxNavigationApp.detach(owner)
            }

            override fun onStop(owner: LifecycleOwner) {
                MapboxNavigationApp.detach(owner)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                MapboxNavigationApp.detach(owner)
            }

            override fun onStart(owner: LifecycleOwner) {
                MapboxNavigationApp.attach(owner)
            }
        })

//        MapboxNavigationApp.current()?.startTripSession()

    }

    private fun getRoute(origin: Point, destination: Point) {


        val routeOptions = RouteOptions.builder()
// applies the default parameters to route options
            .applyDefaultNavigationOptions().applyLanguageAndVoiceUnitOptions(this)
// lists the coordinate pair i.e. origin and destination
// If you want to specify waypoints you can pass list of points instead of null
            .coordinatesList(listOf(origin, destination))
// set it to true if you want to receive alternate routes to your destination
            .alternatives(false)

// provide the bearing for the origin of the request to ensure
// that the returned route faces in the direction of the current user movement
            .bearingsList(
                listOf(
                    Bearing.builder().angle(myLiveBearing.value!!).degrees(45.0).build(), null
                )
            ).build()
        mapboxNavigation.requestRoutes(routeOptions, object : NavigationRouterCallback {
            override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
                // This particular callback is executed if you invoke
                // mapboxNavigation.cancelRouteRequest()

            }

            override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {

                Log.e("LOG_TAG", "route request failed with $reasons")

            }

            override fun onRoutesReady(
                routes: List<NavigationRoute>, routerOrigin: RouterOrigin
            ) {
                routeLineApi.setNavigationRoutes(routes) { value ->
                    routeLineView.renderRouteDrawData(mapView.getMapboxMap().getStyle()!!, value)
                }

            }

        }

        )
        mapboxNavigation.registerRouteProgressObserver(routeProgressObserver)
    }

    //BIT MAP CONVERTER END
//GeoCoding MapBox
    private fun findAddress(point: Point) {

        val options = ReverseGeoOptions(
            center = point,
            limit = 1
        )


        searchRequestTask = searchEngine.search(options, searchCallback)

        val options2 = ReverseGeoOptions(
            center = myLiveLocation.value!!,
            limit = 1
        )
        searchRequestTask2 = searchEngine.search(options2, searchCallback2)
    }
}