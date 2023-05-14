package com.example.easyway.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Model.Location
import com.example.easyway.Model.SearchHistory
import com.example.easyway.R
import com.example.easyway.RecycleViews.SavedLocationAdapter
import com.example.easyway.RecycleViews.SearchHistoryAdapter
import com.example.easyway.ViewModel.InternalDbViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class saved_places : AppCompatActivity() {
    private val viewModel by viewModels<InternalDbViewModel>()
    lateinit var RV: RecyclerView
    lateinit var adapter: SavedLocationAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_places)
        val removed_all_saved_places = findViewById<FrameLayout>(R.id.remove_all_saved)
        RV=findViewById(R.id.saved_places_rv)
        RV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        viewModel.fetchSavedLocations()
        viewModel.fetchedSavedLocationsResult.observe(this)
        {
            when(it)
            {
                null ->{
                    val Location: Location = Location(1,0.0,0.0,"","No saved locations yet","Favorite")
                    val  initList:  MutableList<Location> = mutableListOf(Location)
                    adapter = SavedLocationAdapter(initList)
                    RV.adapter = adapter
                }
                else ->
                {
                    adapter = SavedLocationAdapter(it)
                    RV.adapter = adapter
                }

            }
        }
        val back_arrow = findViewById<ImageView>(R.id.back_button)
        back_arrow.setOnClickListener {
            val intentt = Intent(this, Home::class.java)
            startActivity(intentt)

        }
        removed_all_saved_places.setOnClickListener{
          Log.w("clicked","clicked")
            MaterialAlertDialogBuilder(this)
            .setTitle("Delete all")
            .setMessage("Are you sure you want to delete all the saved places ? ")
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("Delete") {
                    dialog, which ->
                viewModel.DeleteAllFavorites()
                RV.adapter!!.notifyDataSetChanged()
            }.show()
        }

    }
}