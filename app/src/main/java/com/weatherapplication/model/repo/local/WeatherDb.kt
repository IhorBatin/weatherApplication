package com.weatherapplication.model.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.weatherapplication.model.data.CityName

@Database(entities = [CityName::class], version = 1, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object{

        @Volatile
        private var INSTANCE: WeatherDb? = null

        fun getDataBase(context: Context): WeatherDb? {
            if(INSTANCE == null){
                synchronized(WeatherDb::class.java){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WeatherDb::class.java, "us_cities_database"
                        )
                            .createFromAsset("database/us_cities_database.db")
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}