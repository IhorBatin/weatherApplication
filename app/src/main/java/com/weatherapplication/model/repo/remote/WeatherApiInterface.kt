package com.weatherapplication.model.repo.remote

import com.weatherapplication.model.data.CityCoordinates
import com.weatherapplication.model.data.WeatherForLocationResponse
import com.weatherapplication.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("/geo/1.0/direct?limit=1&appid=$API_KEY")
    suspend fun getCityLatLong(
        @Query("q") cityName: String
    ): List<CityCoordinates>

    @GET("/data/2.5/weather?appid=$API_KEY&units=imperial")
    suspend fun getWeatherForLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ) : WeatherForLocationResponse
}