package com.weatherapplication.model.repo.remote

import com.weatherapplication.model.data.GeocodingResponse
import com.weatherapplication.model.data.GeocodingResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("/geo/1.0/direct?q=brooklyn, ny, us&limit=1&appid=76c30fb34ad592ba2418b54d5a445e72")
    suspend fun getCityLatLong(): List<GeocodingResponseItem>

    companion object {
        const val API_KEY = "76c30fb34ad592ba2418b54d5a445e72"
    }
}