package com.ecohabits.di

import com.ecohabits.data.remote.ai.OpenRouterChallengeGenerator
import com.ecohabits.domain.repository.ChallengeGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiModule {

    @Binds
    @Singleton
    abstract fun bindChallengeGenerator(
        impl: OpenRouterChallengeGenerator
    ): ChallengeGenerator
}
