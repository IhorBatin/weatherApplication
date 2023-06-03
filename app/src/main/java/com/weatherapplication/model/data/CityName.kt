package com.weatherapplication.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "us_cities")
data class CityName(

    val fullCityName: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
