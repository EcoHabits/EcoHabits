package com.ecohabits.di

import com.ecohabits.data.repository.HabitRepositoryImpl
import com.ecohabits.data.repository.WeatherRepositoryImpl
import com.ecohabits.domain.repository.HabitRepository
import com.ecohabits.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHabitRepository(impl: HabitRepositoryImpl): HabitRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository
}

