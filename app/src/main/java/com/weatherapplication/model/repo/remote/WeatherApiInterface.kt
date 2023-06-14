package com.weatherapplication.model.repo.remote

import com.weatherapplication.model.data.CityCoordinates
import com.weatherapplication.model.data.WeatherForLocationResponse
import com.weatherapplication.model.data.WeatherForecastResponse
import com.weatherapplication.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("/geo/1.0/direct?limit=1&appid=$API_KEY")
    suspend fun getCityLatLong(
        @Query("q") cityName: String
    ): List<CityCoordinates>

    @GET("/data/2.5/weather?appid=$API_KEY")
    suspend fun getWeatherForLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String
    ) : WeatherForLocationResponse

    @GET("/data/2.5/forecast?appid=$API_KEY")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String,
        @Query("cnt") count: Int
    ) : WeatherForecastResponse
}