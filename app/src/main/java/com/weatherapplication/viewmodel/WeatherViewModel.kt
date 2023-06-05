package com.weatherapplication.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.model.data.CityCoordinates
import com.weatherapplication.model.data.City
import com.weatherapplication.model.data.WeatherForLocationResponse
import com.weatherapplication.model.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(
    private val application: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(application) {

    val possibleCityOptions = mutableListOf<City>()
    val selectedCityName = mutableStateOf("")
    val doesCityExist = mutableStateOf(false)
    val shouldShowError = mutableStateOf(false)
    val selectedCityLonLat = mutableStateOf<CityCoordinates?>(null)
    val weatherData = mutableStateOf(WeatherForLocationResponse())


    // val _selectedCityLonLat = MutableLiveData<CityCoordinates>()
    // var selectedCityLonLat: LiveData<CityCoordinates> = _selectedCityLonLat

    fun setSelectedCityName(name: String) {
        selectedCityName.value = name
    }

    fun updateLikeCityNames(cityName: String) {
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
            val usCityName = "${selectedCityName.value}, US"
            val response = repository.getLatLongForCity(usCityName)
            println("Response -> $response")
            updateCurrentLonLat(response[0])
            updateWeatherForCity()
        }
    }

    fun updateCurrentLonLat(coordinates: CityCoordinates) {
        selectedCityLonLat.value = coordinates
    }

    fun updateWeatherForCity() {
        viewModelScope.launch {
            val resp = selectedCityLonLat.value?.let { repository.getCityWeather(it) }
            println(resp)
            if (resp != null) {
                weatherData.value = resp
            }
        }
    }

    fun onItemClick(city: City) {
        selectedCityName.value = city.fullCityName
        possibleCityOptions.clear()
    }

    fun onClearClick() {
        selectedCityName.value = ""
        possibleCityOptions.clear()
    }
}