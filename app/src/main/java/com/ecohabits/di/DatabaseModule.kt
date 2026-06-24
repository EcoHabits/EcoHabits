package com.ecohabits.di

import android.content.Context
import com.ecohabits.data.local.dao.UserContextDao
import com.ecohabits.data.local.dao.ChallengeDao
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
    fun provideWeatherChallengeDao(database: AppDatabase): ChallengeDao{
        return database.challengeDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserContextDao{
        return database.userContextDao()
    }
}