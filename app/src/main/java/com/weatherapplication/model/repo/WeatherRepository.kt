package com.weatherapplication.model.repo

import com.weatherapplication.model.data.CityCoordinates
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

    fun checkEnteredCityExists(selectedCityName: String) = weatherDao.checkCityExists(selectedCityName)

    suspend fun getLatLongForCity(cityName: String) = apiInterface.getCityLatLong(cityName)

    suspend fun getCityWeather(cityCoordinates: CityCoordinates) =
        apiInterface.getWeatherForLocation(cityCoordinates.lat, cityCoordinates.lon)

}