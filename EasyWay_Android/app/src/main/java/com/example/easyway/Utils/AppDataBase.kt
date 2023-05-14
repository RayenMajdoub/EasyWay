package com.example.easyway.Utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easyway.Dao.LocationDao
import com.example.easyway.Dao.SearchHistoryDao
import com.example.easyway.Model.Location
import com.example.easyway.Model.SearchHistory

@Database(entities = [Location::class,SearchHistory::class], version = 5)
abstract class AppDataBase : RoomDatabase() {
    abstract fun LocationDao(): LocationDao
    abstract fun SearchHistoryDao(): SearchHistoryDao
    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDataBase::class.java, "EasyWay")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return instance!!
        }
    }
}