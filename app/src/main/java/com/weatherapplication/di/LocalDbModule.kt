package com.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.weatherapplication.model.repo.local.WeatherDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): WeatherDb {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDb::class.java, "us_cities_database"
        )
            .createFromAsset("database/us_cities_database.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: WeatherDb) = db.weatherDao()
}