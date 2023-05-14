package com.example.easyway.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easyway.Model.Location
import com.example.easyway.Model.SearchHistory
import com.example.easyway.Utils.AppDataBase
import com.example.easyway.Utils.DataBaseHelper
import com.example.easyway.Utils.DataBaseHelperImpl
import kotlinx.coroutines.launch

class InternalDbViewModel(application: Application) : AndroidViewModel(application) {
    private val DataBaseHelper: DataBaseHelper =
        DataBaseHelperImpl(AppDataBase.getDatabase(application))
    val fetchSearchedLocationsResult: MutableLiveData<MutableList<SearchHistory>> = MutableLiveData()
    val fetchedSavedLocationsResult: MutableLiveData<MutableList<Location>> = MutableLiveData()
    init {
        fetchSearchedLocations()
    }

    fun fetchSearchedLocations() {
        viewModelScope.launch {
            try {
                Log.w("ViewModelfech search", "success")
                val res=DataBaseHelper.getSearchedLocations()
                Log.w("ViewModelfech search", "success")
                fetchSearchedLocationsResult.value=res
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
    }    fun fetchSavedLocations() {
        viewModelScope.launch {
            try {
                val res=DataBaseHelper.getAllFavorites()
                fetchedSavedLocationsResult.value=res
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
    }
    fun insertSearchedLocation(SearchLocation:SearchHistory) {
        viewModelScope.launch {
            try {
                DataBaseHelper.insertSearchHistory(SearchLocation)
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
    }
    fun saveLocation(location: Location) {
        viewModelScope.launch {
            try {
                DataBaseHelper.insertFavoriteLocation(location)
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
    }
    fun DeleteAllSearch() {
        viewModelScope.launch {
            try {
                DataBaseHelper.deleteall()
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
    }    fun DeleteAllFavorites() {
        viewModelScope.launch {
            try {
                DataBaseHelper.deleteallFavorites()
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
    }
    fun deleteSearchHistory(SearchLocation: SearchHistory) {
        viewModelScope.launch {
            try {
                DataBaseHelper.deleteSearchHistory(SearchLocation)
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
    }
    fun getSavedLocationByTag(tag:String) :Location?{
        var Location:Location? = null
        viewModelScope.launch {
            try {
                Location= DataBaseHelper.getLocationByTag(tag)
            } catch (e: Exception) {
                Log.w("ViewModel", "Error$e")
            }

        }
     return Location
    }
}