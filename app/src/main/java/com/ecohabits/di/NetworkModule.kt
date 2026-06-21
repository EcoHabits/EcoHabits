package com.ecohabits.di

import com.ecohabits.data.remote.AiApi
import com.ecohabits.data.remote.LocationApi
import com.ecohabits.data.remote.WeatherApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AiRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    // --- WEATHER API CONFIG ---
    
    @Provides
    @Singleton
    @WeatherRetrofit
    fun provideWeatherRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(@WeatherRetrofit retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    // --- LOCATION API CONFIG ---

    @Provides
    @Singleton
    @LocationRetrofit
    fun provideLocationRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideLocationApi(@LocationRetrofit retrofit: Retrofit): LocationApi {
        return retrofit.create(LocationApi::class.java)
    }

    // --- AI API CONFIG (OpenRouter) ---

    @Provides
    @Singleton
    @AiRetrofit
    fun provideAiRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://openrouter.ai/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideAiApi(@AiRetrofit retrofit: Retrofit): AiApi {
        return retrofit.create(AiApi::class.java)
    }
}
