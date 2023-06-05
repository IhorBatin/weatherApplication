package com.weatherapplication.model.repo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherapplication.model.data.City
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecordToDb(city: City)

    @Query(
        "SELECT * FROM us_cities WHERE fullCityName LIKE :cityName || '%' ORDER BY fullCityName ASC"
    )
    fun queryLikeCityName(cityName: String): Flow<List<City>>

    @Query(
        "SELECT * FROM us_cities WHERE fullCityName = :selectedCityName"
    )
    fun checkCityExists(selectedCityName: String): Flow<List<City>>
}