package com.weatherapplication.di

import android.content.Context
import com.weatherapplication.model.repo.WeatherRepository
import com.weatherapplication.model.repo.local.WeatherDao
import com.weatherapplication.model.repo.remote.WeatherApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        weatherApiInterface: WeatherApiInterface,
        weatherDao: WeatherDao
    ) : WeatherRepository {
        return WeatherRepository(
            weatherDao,
            weatherApiInterface
        )
    }
}