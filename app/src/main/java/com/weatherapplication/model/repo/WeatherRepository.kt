package com.weatherapplication.model.repo

import com.weatherapplication.model.repo.local.WeatherDao
import com.weatherapplication.model.repo.remote.WeatherApiInterface
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(
    private val weatherDao: WeatherDao,
    private val apiInterface: WeatherApiInterface
) {

    fun getCityNames(cityName: String) = weatherDao.queryLikeCityName(cityName)

    suspend fun getLatLongForCity(cityName: String) = apiInterface.getCityLatLong()

}