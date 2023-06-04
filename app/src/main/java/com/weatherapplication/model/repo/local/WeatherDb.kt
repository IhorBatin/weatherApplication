package com.weatherapplication.model.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weatherapplication.model.data.CityName

@Database(entities = [CityName::class], version = 1, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}