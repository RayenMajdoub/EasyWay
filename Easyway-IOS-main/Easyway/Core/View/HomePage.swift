//
//  HomePage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 15/3/2023.
//

import SwiftUI
import MapKit
import SwiftDrawer

struct HomePage: View {
  @State var sheetIsPresented = false
   // @State var tracking:MapUserTrackingMode = .follow
    //@State private var route: MKRoute?
    //@State private var annotation:MKPointAnnotation?
    @State var offset:CGFloat = 0
   
    var body: some View {
        NavigationView{
            ZStack(alignment: Alignment(horizontal: .center, vertical: .bottom), content: {
                MapView()  .ignoresSafeArea(.all)
                GeometryReader{
                    reader in
                    VStack{
                        
                        BottomSheet()
                            .offset(y:reader.frame(in: .global).height-110)
                            .offset(y:offset)
                            .gesture(DragGesture().onChanged({
                                (value) in
                                withAnimation{
                                    if value.startLocation.y > reader.frame(in: .global).midX{
                                        offset = value.translation.height
                                    }
                                    if value.startLocation.y < reader.frame(in: .global).midX{
                                        offset = (-reader.frame(in: .global).height+110) + value.translation.height
                                    }
                                }
                            }).onEnded({(value) in
                                withAnimation{
                                    if value.startLocation.y > reader.frame(in: .global).midX{
                                        if -value.translation.height > reader.frame(in: .global).midX
                                        {
                                            offset = (-reader.frame(in: .global).height +  110)
                                            return
                                        }
                                        offset = 0
                                    }
                                    if value.startLocation.y < reader.frame(in: .global).midX{
                                        if value.translation.height < reader.frame(in: .global).midX
                                        {
                                            offset = (-reader.frame(in: .global).height +  110)
                                            return
                                        }
                                        offset = 0
                                    }
                                }
                            })
                            )
                        
                    }
                }
                .ignoresSafeArea(.all,edges: .bottom)
             
            })
            
        }
    }
    
    
    struct DefaultView: View {
        @State var searchtext = ""
        @Binding var selectsheet:Int
        @EnvironmentObject var searchviewModel : LocationSearchModel
        var body: some View {
            
            HStack(spacing: 15)
            {
                Image(systemName: "magnifyingglass")
                    .font(.system(size: 22))
                    .foregroundColor(.gray)
                    .padding(.leading)
                TextField("Search Place",text: $searchviewModel.queryFragment)
                    .padding(.vertical,10)
                    .padding(.horizontal)
                    .background(BlurView(style: .systemMaterial))
                    .cornerRadius(15)
                    .padding()
            }
            HStack{
                Button(
                    action: {
                        self.selectsheet = 1
                    })
                {
                    VStack{
                        LottieView (name: LottieNames.navigate_points, loopMode:
                                .loop)
                        .frame(width: 80,height: 80)
                        .scaleEffect(0.3)
                        .offset(x:-20)
                        
                        Text("Get me \nsomwhere")
                            .font(.custom(Fonts.Font1, size: 20))
                            .padding(.vertical, 18)
                            .foregroundColor(.white)
                            .multilineTextAlignment( .leading)
                            .offset(x:-25)
                        
                    }
                    .frame(width: 200, height: 200)
                    .background(Color(Colors.ColorPrimary).opacity(0.9))
                    .cornerRadius(20)
                    .padding(5)
                    
                    
                    
                    
                }
                Button(
                    action: {
                        print("hello")
                    })
                {
                    VStack{
                        LottieView (name: LottieNames.home, loopMode:
                                .loop)
                        .frame(width: 80,height: 80)
                        .scaleEffect(0.25)
                        .offset(x:-20)
                        
                        
                        Text("Home")
                            .font(.custom(Fonts.Font1, size: 20))
                            .padding(.vertical, 18)
                            .foregroundColor(.white)
                            .multilineTextAlignment( .leading)
                            .offset(x:-10)
                        
                    }
                    .frame(width: 150, height: 200)
                    .background(Color(Colors.Blue).opacity(0.9))
                    .cornerRadius(20)
                    .padding(5)
                }
            }
            
            
            ScrollView(.vertical,showsIndicators: false,content: {
                LazyVStack(alignment: .leading, spacing: 15,content: {
                    ForEach(searchviewModel.results, id:\.self){
                        result in
                        searchCell(title: result.title, subtitle: result.subtitle).onTapGesture {
                            searchviewModel.selectLocation(result)
                        }
                    }
                })
            }
                       
            ).padding()
            
                
            
        }
    }
    struct searchCell : View{
        let title :String
        let subtitle :String
        var body: some View
        {
            HStack
            {
                Image(systemName: "mappin.circle.fill")
                    .resizable()
                    .foregroundColor(Color(Colors.Blue))
                    .accentColor(.white)
                    .frame(width: 40,height: 40)
                VStack(alignment: .leading)
                {
                    Text(title)
                        .font(.body)
                    Text(subtitle)
                        .font(.system(size:15))
                        .foregroundColor(.gray)
                    Divider()
                }.padding(.leading ,8)
                    .padding(.vertical ,8)
            }
            .padding(.leading)
        }
    }
    
    struct GetMeSomewhere: View {
        
        @State private var from = ""
        @State private var to = ""
        @State private var showingVoyageView = false
        var body: some View {
            
            Text("Where to go Today ?")
                .font(.custom(Fonts.Font1, size: 42))
                .frame(maxWidth: .infinity , alignment: .leading)
                .frame(height: 220)
                .padding(20)
            HStack(spacing: 15)
            {
                Text("From")
                    .font(.system(size: 22))
                    .foregroundColor(.gray)
                    .padding(.leading)
                TextField("Search Place",text: $from)
                    .padding(.vertical,10)
                    .padding(.horizontal)
                    .background(BlurView(style: .systemMaterial))
                    .cornerRadius(15)
                    .padding()
            }
            HStack(spacing: 15)
            {
                
               
                Text("To")
                    .font(.system(size: 22))
                    .foregroundColor(.gray)
                    .padding(.leading)
                TextField("Search Place",text: $to)
                    .padding(.vertical,10)
                    .padding(.horizontal)
                    .background(BlurView(style: .systemMaterial))
                    .cornerRadius(15)
                    .padding()
            }
            Button(
                action: {
                    showingVoyageView = true
 
                })
            {
                    Text("Go Now")
                        .font(.custom(Fonts.Font1, size: 20))
                        .padding(.vertical, 18)
                        .foregroundColor(.white)
                        .multilineTextAlignment( .leading)
                   
                    
                }
                .frame(maxWidth:.infinity)
                .background(Color(Colors.AccentDarkPink).opacity(0.9))
                .cornerRadius(20)
                .padding(5)
                .frame(height: 100)
                .fullScreenCover(isPresented: $showingVoyageView, content: {
                    TicketListPage(from: self.$from, to: self.$to)
                        })
            Spacer()
        }
        
    }
    
    struct BottomSheet: View {
        @State private var selectsheet = 0
        var body: some View {
            VStack{
                Capsule( )
                    .fill(Color(Colors.ColorSecondary).opacity(0.5))
                    .frame(width: 50, height: 5)
                    .padding(.top)
                    .padding(.bottom,5)
                
                switch selectsheet
                {
                case 0:
                    DefaultView(selectsheet: $selectsheet)
                case 1:
                    GetMeSomewhere()
                    
                default:
                    DefaultView(selectsheet: $selectsheet)
                }
                
                
            }
            .background(BlurView(style: .systemMaterial))
            .cornerRadius(15)
        }
        
    }
}
struct BlurView: UIViewRepresentable {
    let style :UIBlurEffect.Style
    func makeUIView(context:Context)->UIVisualEffectView
    {
        let view = UIVisualEffectView(effect:UIBlurEffect(style: style))
        return view
    }
    func updateUIView(_ uiView: UIVisualEffectView, context: Context) {
        
    }
}
struct MapView : UIViewRepresentable
{
  let manager = LocationManager()
let mapView = MKMapView()
    @EnvironmentObject var locationviewModel : LocationSearchModel
    func makeUIView(context: Context) -> some UIView {
        mapView .delegate = context.coordinator
        mapView.showsUserLocation = true
        mapView.userTrackingMode = .follow
        mapView.setRegion(manager.region, animated: true)
        return mapView
    }
    func updateUIView(_ uiView: UIViewType, context: Context) {
        if let selectedLocation = locationviewModel.selectedLocationCoordinate
        {
            context .coordinator.addAndSelectAnnotation(withCoordinate: selectedLocation)
            context .coordinator.configurePolyline(withDestinationCoordinate: selectedLocation)
            print("selected in mapview generator \(selectedLocation)")
        }
    }
    func makeCoordinator() -> MapCoordinator {
        return MapCoordinator(parent: self)
    }
}
extension MapView {
    class MapCoordinator:NSObject,MKMapViewDelegate
    {let searchmodel = LocationSearchModel()
        var userlocationCoordinate : CLLocationCoordinate2D?
        let parent:MapView
        init(parent: MapView) {
            self.parent = parent
            super.init()
        }
        func mapView(_ mapView: MKMapView, didUpdate userLocation: MKUserLocation) {
            self.userlocationCoordinate = userLocation.coordinate
            let region = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: userLocation.coordinate.latitude, longitude: userLocation.coordinate.longitude), span: MKCoordinateSpan(latitudeDelta: 0.05, longitudeDelta: 0.05))
            parent.mapView.setRegion(region, animated: true)
        }
        func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
            let line = MKPolylineRenderer(overlay: overlay)
            line.strokeColor = .systemBlue
            line.lineWidth = 6
            return line
        }
        func addAndSelectAnnotation(withCoordinate coordinate:CLLocationCoordinate2D)
        {
            parent.mapView.removeAnnotations(parent.mapView.annotations)
            let annotation = MKPointAnnotation()
            annotation.coordinate = coordinate
            
           parent.mapView.addAnnotation(annotation)
            //parent.mapView.selectAnnotation(annotation, animated: true)
            
            parent.mapView.showAnnotations(parent.mapView.annotations, animated: true)
        }
        func mapView(_ mapView: MKMapView, didSelect annotation: MKAnnotation) {
            if let title = annotation.title {
                let alertController = UIAlertController(title: title, message: "Do you want to go to this location?", preferredStyle: .alert)
                alertController.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
                alertController.addAction(UIAlertAction(title: "Go Now!", style: .default, handler: { _ in
                  
                    
                //    self.searchmodel.userlocationTitle() HELPPPPPP
                    
                      
                }))
                if let window = UIApplication.shared.windows.first(where: { $0.isKeyWindow }) {
                    if let rootViewController = window.rootViewController {
                        rootViewController.present(alertController, animated: true, completion: nil)
                    }
                }
            }
        }




        func configurePolyline(withDestinationCoordinate coordinate : CLLocationCoordinate2D)

        {
            guard let userlocation = self.userlocationCoordinate else {return }
            getDestinationRoute(from:userlocation , to: coordinate) { route in
                self.parent.mapView.addOverlay(route.polyline)
                //self.parent.
            }
        }
        func getDestinationRoute(from userLocation:CLLocationCoordinate2D,to destination:CLLocationCoordinate2D,completion:@escaping(MKRoute)->Void)
        {
            let userPlaceMark = MKPlacemark(coordinate: userLocation)
            let destPlaceMark = MKPlacemark(coordinate: destination)
            let request = MKDirections.Request()
            request.source = MKMapItem(placemark: userPlaceMark)
            request.destination = MKMapItem(placemark: destPlaceMark)
            request.transportType = .automobile
            request.requestsAlternateRoutes = true
            let directions = MKDirections(request: request)
            directions.calculate{
                response,error in
                if let error = error {
                    print("error in directions getdestinationRoute fnc \(error)")
                    return
                }
                guard let route = response?.routes.first else {
                    return
                }
                completion(route)
            }
        }
    }
}

struct HomePage_Previews: PreviewProvider {
    
    static var previews: some View {
        HomePage()
    }
}
