package com.example.easyway.View

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Model.SearchHistory
import com.example.easyway.R
import com.example.easyway.RecycleViews.SearchHistoryAdapter
import com.example.easyway.RecycleViews.TicketsAdapter
import com.example.easyway.ResponseUtils.VoyagesListResponse
import com.example.easyway.ViewModel.InternalDbViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * A simple [Fragment] subclass.
 * Use the [Button_lists_sheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class Button_lists_sheet : Fragment() {
    lateinit var adapter: SearchHistoryAdapter
    private val viewModel by viewModels<InternalDbViewModel>()

    lateinit var RV: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_button_lists_sheet, container, false)


        ////SEARCH RV
        val Home_location = viewModel.getSavedLocationByTag("HOME")
        val Work_location = viewModel.getSavedLocationByTag("WORK")
        RV = view.findViewById(R.id.rv_search)
        RV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewModel.fetchSearchedLocationsResult.observe(requireActivity()) {
            when (it) {
                null -> {
                    val SearchHistory: SearchHistory =
                        SearchHistory(1, 0.0, 0.0, "", "No Search History Yet")
                    val initList: MutableList<SearchHistory> = mutableListOf(SearchHistory)
                    adapter = SearchHistoryAdapter(initList)
                    RV.adapter = adapter
                }
                else -> {
                    adapter = SearchHistoryAdapter(it)
                    RV.adapter = adapter
                }

            }
        }
        //BUTTONS
        val testbutton: FrameLayout = view.findViewById(R.id.testbutton)
        val GetMeSomeWhere_fragment = GetMeSomeWhere()
        testbutton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, GetMeSomeWhere_fragment).addToBackStack(null)
                commit()
            }
        }
        val home_btn = view.findViewById<FrameLayout>(R.id.homebtn)
        home_btn.setOnClickListener {
            if (Home_location == null) {

                MaterialAlertDialogBuilder(requireActivity())
                    .setIcon(R.drawable.ic_add_something)
                    .setMessage("You need to add your home adress")
                    .setNeutralButton("Cancel") { dialog, which -> dialog.dismiss()}
                    .show()
            } else {
                val intent = Intent(activity, Tickets_list::class.java)
                intent.putExtra("Type", "all")
                intent.putExtra("DeparturePoint", Home.myLocationAdresse.value)
                intent.putExtra("ArrivalPoint", Home_location.place)
                startActivity(intent)

            }
        }
        val workbtn = view.findViewById<FrameLayout>(R.id.workbtn)

        workbtn.setOnClickListener {
            if (Work_location == null) {

                MaterialAlertDialogBuilder(requireActivity())
                    .setIcon(R.drawable.ic_add_something)
                    .setMessage("You need to add your work address")
                    .setNeutralButton("Cancel") { dialog, which -> dialog.dismiss()}
                    .show()
            } else {
                val intent = Intent(activity, Tickets_list::class.java)
                intent.putExtra("Type", "all")
                intent.putExtra("DeparturePoint", Home.myLocationAdresse.value)
                intent.putExtra("ArrivalPoint", Work_location.place)
                startActivity(intent)

            }
            val saved_locations = view.findViewById<FrameLayout>(R.id.saved_locations)
            saved_locations.setOnClickListener {
                val intent = Intent(activity, saved_places::class.java)
                startActivity(intent)
            }
        }


        return view

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSearchedLocations()
        viewModel.fetchSearchedLocationsResult.observe(requireActivity()) {
            when (it) {
                null -> {
                    val SearchHistory: SearchHistory =
                        SearchHistory(1, 0.0, 0.0, "", "No Search History Yet")
                    val initList: MutableList<SearchHistory> = mutableListOf(SearchHistory)
                    adapter = SearchHistoryAdapter(initList)
                    RV.adapter = adapter
                }
                else -> {
                    adapter = SearchHistoryAdapter(it)
                    RV.adapter = adapter
                }

            }
        }
    }
    /* companion object {
         /**
          * Use this factory method to create a new instance of
          * this fragment using the provided parameters.
          *
          * @param param1 Parameter 1.
          * @param param2 Parameter 2.
          * @return A new instance of fragment Button_lists_sheet.
          */
         // TODO: Rename and change types and number of parameters
         @JvmStatic
         fun newInstance(param1: String, param2: String) =
             Button_lists_sheet().apply {
                 arguments = Bundle().apply {
                     putString(ARG_PARAM1, param1)
                     putString(ARG_PARAM2, param2)
                 }
             }
     }*/
}
