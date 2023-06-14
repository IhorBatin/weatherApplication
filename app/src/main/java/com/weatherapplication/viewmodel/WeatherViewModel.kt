package com.weatherapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.model.data.CityCoordinates
import com.weatherapplication.model.data.City
import com.weatherapplication.model.data.WeatherForLocationResponse
import com.weatherapplication.model.data.WeatherForecastResponse
import com.weatherapplication.model.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(
    application: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(application) {

    private val selectedCityLonLat = mutableStateOf<CityCoordinates?>(null)
    val possibleCityOptions = mutableListOf<City>()
    val selectedCityName = mutableStateOf("")
    val doesCityExist = mutableStateOf(false)
    val shouldShowError = mutableStateOf(false)
    val showLoadingIndicator = mutableStateOf(true)
    val currentWeatherData = mutableStateOf(WeatherForLocationResponse())
    val forecastWeatherData = mutableStateOf(WeatherForecastResponse())
    val unitsSwitchState = mutableStateOf(false)

    fun setSelectedCityName(name: String) {
        selectedCityName.value = name
    }

    fun updateLikeCityNames(cityName: String) {
        // Making flag false, as we need to check again for new entry
        doesCityExist.value = false
        viewModelScope.launch {
            if (cityName.isNotEmpty()) {
                repository.getCityNames(cityName).collect {
                    possibleCityOptions.clear()
                    possibleCityOptions.addAll(it)
                }
            } else
                possibleCityOptions.clear()
        }
    }

    fun checkCityExists() {
        viewModelScope.launch {
            repository.checkEnteredCityExists(selectedCityName.value).collect {
                doesCityExist.value = it.isNotEmpty()
                shouldShowError.value = it.isEmpty()
            }
        }
    }

    fun geocodeByCityName() {
        viewModelScope.launch {
            showLoadingIndicator.value = true
            // Appending 'US' as we looking for cities within US only
            val usCityName = "${selectedCityName.value}, US"
            try {
                val response = repository.getLatLongForCity(usCityName)
                if (response.isNotEmpty()) {
                    updateCurrentLonLat(response[0])
                    updateWeatherData()
                }
            } catch (exception: Exception) {
                println(exception.message)
            }

        }
    }

    fun updateCurrentLonLat(coordinates: CityCoordinates) {
        selectedCityLonLat.value = coordinates
    }

    fun updateWeatherData() {
        viewModelScope.launch {
            showLoadingIndicator.value = true
            try {
                val resp = selectedCityLonLat.value?.let {
                    repository.getCurrentWeather(it, getUnits())
                }
                val forecastResp = selectedCityLonLat.value?.let {
                    repository.getWeatherForecast(it, getUnits(), 5)
                }
                if (resp != null) {
                    currentWeatherData.value = resp
                    showLoadingIndicator.value = false
                }
            } catch (exception: Exception) {
                println(exception.message)
            }
        }
    }

    fun updateForecast() {

    }

    fun onCityListItemClick(city: City) {
        selectedCityName.value = city.fullCityName
        possibleCityOptions.clear()
    }

    fun clearCityOptions() {
        selectedCityName.value = ""
        possibleCityOptions.clear()
    }

    private fun getUnits(): String {
        return when (unitsSwitchState.value) {
            true -> "imperial"
            false -> "metric"
        }
    }

    fun updateUnits(value: Boolean) {
        unitsSwitchState.value = value
    }
}