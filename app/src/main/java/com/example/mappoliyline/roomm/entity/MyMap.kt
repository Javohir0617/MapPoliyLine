package com.example.mappoliyline.roomm.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyMap(
@PrimaryKey(autoGenerate = true)
var id: Int = 0,
var leng: Double,
var height: Double)
