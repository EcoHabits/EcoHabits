package com.ecohabits.di

import com.ecohabits.data.repository.AuthRepositoryImpl
import com.ecohabits.data.repository.ChallengeRepositoryImpl
import com.ecohabits.data.repository.LocationRepositoryImpl
import com.ecohabits.data.repository.WeatherRepositoryImpl
import com.ecohabits.domain.repository.AuthRepository
import com.ecohabits.domain.repository.ChallengeRepository
import com.ecohabits.domain.repository.LocationRepository
import com.ecohabits.domain.repository.UserRepository
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
    abstract fun bindChallengeRepository(impl: ChallengeRepositoryImpl): ChallengeRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepository): UserRepository
}

