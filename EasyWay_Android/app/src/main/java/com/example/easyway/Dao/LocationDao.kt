package com.example.easyway.Dao

import androidx.room.*
import com.example.easyway.Model.FavoriteType
import com.example.easyway.Model.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Location: Location)

    @Delete
    fun delete(Location: Location)

    @Update
    fun update(Location: Location)

    @Query("SELECT * FROM Location")
    fun getAllFavorites(): MutableList<Location>

    @Query("SELECT * FROM Location  Where tag=:tag")
    fun getLocationByTag(tag: String): Location?
    @Query("DELETE FROM Location")
    fun deleteallfavorties()

}