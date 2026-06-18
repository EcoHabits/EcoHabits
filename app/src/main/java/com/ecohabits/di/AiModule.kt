package com.ecohabits.di

import com.ecohabits.BuildConfig
import com.ecohabits.data.remote.ai.GeminiChallengeGenerator
import com.ecohabits.domain.repository.ChallengeGenerator
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiModule {

    @Binds
    @Singleton
    abstract fun bindChallengeGenerator(
        geminiChallengeGenerator: GeminiChallengeGenerator
    ): ChallengeGenerator

    companion object {
        @Provides
        @Singleton
        fun provideGenerativeModel(): GenerativeModel {
            return GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = BuildConfig.GEMINI_API_KEY
            )
        }
    }
}
