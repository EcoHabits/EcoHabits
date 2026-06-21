package com.ecohabits.data.mapper

import com.ecohabits.data.remote.dto.ChallengeDto
import com.ecohabits.domain.model.Challenge
import com.ecohabits.domain.model.HabitCategory

fun ChallengeDto.toDomain(): Challenge {
    return Challenge(
        id = idChallenge ?: "",
        title = challengeName,
        description = challengeDescription,
        category = try {
            HabitCategory.valueOf(challengeCategory.uppercase())
        } catch (e: Exception) {
            HabitCategory.RESIDUOS
        },
        points = challengePoints,
        isCompleted = false // Por defecto al descargar de la tabla maestra
    )
}

fun Challenge.toDto(city: String, weather: String): ChallengeDto {
    return ChallengeDto(
        idChallenge = if (id.startsWith("gen_")) null else id, // Si es generado por Gemini, dejamos que Supabase asigne UUID
        challengeName = title,
        challengeDescription = description,
        challengeCategory = category.name,
        cityName = city,
        weatherCondition = weather,
        challengePoints = points
    )
}
