package com.example.easyway.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Model.Location
import com.example.easyway.Model.SearchHistory
import com.example.easyway.R
import com.example.easyway.ViewModel.InternalDbViewModel
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_searched_options.newInstance] factory method to
 * create an instance of this fragment.
 */

class fragment_searched_options : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Bundle? = null
    private var param2: String? = null
    private var param6: String? = null
    private var param3: Int? = null
    private var param4: Double? = null
    private var param5: Double? = null
    private val viewModel by viewModels<InternalDbViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_searched_options, container, false)
        val title = view.findViewById<TextView>(R.id.titre_search)
        title.text = requireArguments().getString("place")

        val button_cancel = view.findViewById<TextView>(R.id.cancel_searched)
        val save_place_button = view.findViewById<FrameLayout>(R.id.save_place_button)
        val save_home = view.findViewById<FrameLayout>(R.id.save_home)
        val save_work = view.findViewById<FrameLayout>(R.id.save_work)
        val remove = view.findViewById<FrameLayout>(R.id.remove_btn)
        val remove_all = view.findViewById<FrameLayout>(R.id.remove_all)
        val Button_lists_sheet = Button_lists_sheet()
        button_cancel.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, Button_lists_sheet)
                commit()
            }
        }

        save_place_button.setOnClickListener {
            val location = Location(
                requireArguments().getInt("id"),
                requireArguments().getDouble("lat"),
                requireArguments().getDouble("lng"),
                requireArguments().getString("adress")!!,
                requireArguments().getString("place")!!,
             "favorite"
            )
            viewModel.saveLocation(location)
            showToast("Location has been saved")

        }
        save_home.setOnClickListener {
            val location = Location(
                requireArguments().getInt("id"),
                requireArguments().getDouble("lat"),
                requireArguments().getDouble("lng"),
                requireArguments().getString("adress")!!,
                requireArguments().getString("place")!!,
                "HOME"
            )
            viewModel.saveLocation(location)
            showToast("Location has been saved")

        }
        save_work.setOnClickListener {
            val location = Location(
                requireArguments().getInt("id"),
                requireArguments().getDouble("lat"),
                requireArguments().getDouble("lng"),
                requireArguments().getString("adress")!!,
                requireArguments().getString("place")!!,
                "WORK"
            )
            viewModel.saveLocation(location)
            showToast("Location has been saved")

        }
        remove.setOnClickListener{
            val searchHistory:SearchHistory = SearchHistory(
                requireArguments().getInt("id"),
                requireArguments().getDouble("lat"),
                requireArguments().getDouble("lng"),
                requireArguments().getString("adress")!!,
                requireArguments().getString("place")!!)
            viewModel.deleteSearchHistory(searchHistory)
            showToast("Deleted")

        }
        remove_all.setOnClickListener{
            viewModel.DeleteAllSearch()
            showToast("Deleted")
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Bundle) =
            fragment_searched_options().apply {
                arguments = Bundle().apply {
                    putAll(param1)
                }
            }
    }
    fun showToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}