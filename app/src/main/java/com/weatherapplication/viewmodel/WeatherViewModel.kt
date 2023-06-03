package com.weatherapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.model.data.CityName
import com.weatherapplication.model.repo.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = WeatherRepository(application)

    val possibleCityOptions = mutableListOf<CityName>()

    fun updateLikeCityNames(cityName: String) {
        if (cityName.isNotEmpty()) {
            viewModelScope.launch {
                repo.getCityName(cityName)?.collect {
                    possibleCityOptions.clear()
                    possibleCityOptions.addAll(it)
                }
            }
        }
    }
}