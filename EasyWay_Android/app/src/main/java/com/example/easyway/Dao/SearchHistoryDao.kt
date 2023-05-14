package com.example.easyway.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.easyway.Model.SearchHistory

@Dao
interface SearchHistoryDao {


        @Insert
        fun insert(Location: SearchHistory)

        @Delete
        fun delete(Location: SearchHistory)

        @Query("SELECT * FROM SearchHistory")
        fun getSearchedLocations():MutableList<SearchHistory>

        @Query("DELETE FROM SearchHistory")
        fun deleteall()


}