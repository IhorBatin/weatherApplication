package com.weatherapplication.model.repo

import android.app.Application
import com.weatherapplication.model.repo.local.WeatherDao
import com.weatherapplication.model.repo.local.WeatherDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class WeatherRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var weatherDao: WeatherDao?

    init {
        val db: WeatherDb? = WeatherDb.getDataBase(application)
        weatherDao = db?.weatherDao()
    }

    fun getCityName(cityName: String) =
        weatherDao?.queryLikeCityName(cityName)

}