package com.ecohabits.di

import android.content.Context
import com.ecohabits.data.local.dao.WeatherChallengeDAO
import com.ecohabits.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return AppDatabase.getDataBase(context)
    }

    @Provides
    fun provideWeatherChallengeDAO(database: AppDatabase): WeatherChallengeDAO{
        return database.weatherChallengeDAO()
    }
}