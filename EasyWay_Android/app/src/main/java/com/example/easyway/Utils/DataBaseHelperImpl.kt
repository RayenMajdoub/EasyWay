package com.example.easyway.Utils
import android.util.Log
import com.example.easyway.Model.Location
import com.example.easyway.Model.SearchHistory

class DataBaseHelperImpl (private val appDataBase: AppDataBase) :DataBaseHelper{
    override suspend fun insertFavoriteLocation(Location: Location) {
        appDataBase.LocationDao().insert(Location)
        Log.i("InsertLocation","Done")
    }

    override suspend fun deleteFavoriteLocation(Location: Location) {
        appDataBase.LocationDao().delete(Location)
        Log.i("DeleteLocation","Done")
    }

    override suspend fun updateFavoriteLocation(Location: Location) {
        appDataBase.LocationDao().update(Location)
        Log.i("UpdateLocation","Done")
    }

    override suspend fun getAllFavorites(): MutableList<Location> {
        Log.i("GetAllfavorites","Done")
       return appDataBase.LocationDao().getAllFavorites()

    }

    override suspend fun getLocationByTag(tag: String): Location? {
        Log.i("InsertLocation","Done")
        return appDataBase.LocationDao().getLocationByTag(tag)

    }

    override suspend fun insertSearchHistory(Location: SearchHistory) {
        appDataBase.SearchHistoryDao().insert(Location)
        Log.i("InsertSearchHistory","Done")
    }

    override suspend fun deleteSearchHistory(Location: SearchHistory) {
        appDataBase.SearchHistoryDao().delete(Location)
        Log.i("DeleteSearchHistory","Done")
    }

    override suspend fun getSearchedLocations(): MutableList<SearchHistory> {
        Log.i("IGetAllsearched","Done")
        return appDataBase.SearchHistoryDao().getSearchedLocations()


    }

    override suspend fun deleteall() {
        appDataBase.SearchHistoryDao().deleteall()
    }

    override suspend fun deleteallFavorites() {
        appDataBase.LocationDao().deleteallfavorties()
    }
}