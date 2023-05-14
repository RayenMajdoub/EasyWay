package com.example.easyway.Utils

import com.example.easyway.Model.Location
import com.example.easyway.Model.SearchHistory

interface DataBaseHelper {

    suspend fun insertFavoriteLocation(Location: Location)

    suspend fun deleteFavoriteLocation(Location: Location)

    suspend fun updateFavoriteLocation(Location: Location)

    suspend fun getAllFavorites(): MutableList<Location>

    suspend fun getLocationByTag(tag: String): Location?

    suspend  fun insertSearchHistory(Location: SearchHistory)

    suspend fun deleteSearchHistory(Location: SearchHistory)

    suspend fun getSearchedLocations():MutableList<SearchHistory>
    suspend fun deleteall()
    suspend fun deleteallFavorites()


}