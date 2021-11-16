package com.example.mappoliyline.roomm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mappoliyline.roomm.dao.DaoMap
import com.example.mappoliyline.roomm.entity.MyMap

@Database(entities = [MyMap::class], version = 1)
abstract class DataBaseHelper : RoomDatabase() {

    abstract fun helper(): DaoMap

    companion object {

        var databaseHelper: DataBaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DataBaseHelper {
            if (databaseHelper == null) {
                databaseHelper = Room.databaseBuilder(context, DataBaseHelper::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()
            }

            return databaseHelper!!
        }

    }
}