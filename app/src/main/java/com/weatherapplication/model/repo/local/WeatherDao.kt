package com.weatherapplication.model.repo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherapplication.model.data.CityName
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecordToDb(city: CityName)

    @Query("SELECT * FROM us_cities WHERE fullCityName LIKE '%' || :cityName || '%'")
    fun queryLikeCityName(cityName: String): Flow<List<CityName>>
}