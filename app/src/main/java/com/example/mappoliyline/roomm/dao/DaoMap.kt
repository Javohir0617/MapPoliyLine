package com.example.mappoliyline.roomm.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mappoliyline.roomm.entity.MyMap


@Dao
interface DaoMap {

    @Insert
    fun addMap(myMap: MyMap)

    @Query("select * from mymap")
    fun getAllMaps(): List<MyMap>
}

