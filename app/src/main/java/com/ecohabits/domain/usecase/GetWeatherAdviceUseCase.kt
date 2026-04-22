package com.ecohabits.domain.usecase

import com.ecohabits.domain.model.WeatherAdvice
import com.ecohabits.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherAdviceUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): List<WeatherAdvice> = repository.getWeatherAdvice()
}

