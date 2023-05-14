package com.example.easyway.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.easyway.Model.SearchHistory
import com.example.easyway.R
import com.example.easyway.ViewModel.InternalDbViewModel
import com.example.easyway.ViewModel.PaymentViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GetMeSomeWhere.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetMeSomeWhere : Fragment() {
    private val viewModel by viewModels<InternalDbViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*       arguments?.let {
                   param1 = it.getString(ARG_PARAM1)
                   param2 = it.getString(ARG_PARAM2)
               }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_get_me_some_where, container, false)

        val back_button: ImageView = view.findViewById(R.id.back_button)
        val Button_lists_sheet = Button_lists_sheet()

        //inflater for recycleView
        //     var inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        back_button.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, Button_lists_sheet)
                commit()
            }
        }

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val departPointValue: TextInputEditText = view.findViewById(R.id.start_point)
        val endPointValue: TextInputEditText = view.findViewById(R.id.end_point)
        val todays_date: TextView = view.findViewById(R.id.todays_date)
        val search_button: Button = view.findViewById(R.id.button_search)
        val date : Date = Date()

        todays_date.text = date.toString()
        Home.OtherMarkerLocation.observe(requireActivity()){

          endPointValue.setText(it)
        }
            Home.myLocationAdresse.observe(requireActivity()){

            departPointValue .setText(it)
        }



        search_button.setOnClickListener {
            val SearchHistory:SearchHistory = SearchHistory(0,0.0,0.0,endPointValue.text.toString(),endPointValue.text.toString())
            viewModel.insertSearchedLocation(SearchHistory)

            val intent = Intent(activity, Tickets_list::class.java)
            intent.putExtra("Type", "all")
            intent.putExtra("DeparturePoint", departPointValue.text.toString())
            intent.putExtra("ArrivalPoint", endPointValue.text.toString())
            startActivity(intent)
        }

        /* companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GetMeSomeWhere.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GetMeSomeWhere().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
    }
}