package com.ecohabits.data.mapper

import com.ecohabits.data.local.entity.ChallengeEntity
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
        isCompleted = false
    )
}

fun Challenge.toEntity(): ChallengeEntity {
    return ChallengeEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        points = points,
        isCompleted = isCompleted
    )
}

fun ChallengeEntity.toDomain(): Challenge {
    return Challenge(
        id = id,
        title = title,
        description = description,
        category = category,
        points = points,
        isCompleted = isCompleted
    )
}

fun Challenge.toDto(city: String, weather: String): ChallengeDto {
    return ChallengeDto(
        idChallenge = null, // Siempre nulo al guardar para que Supabase genere el UUID
        challengeName = title,
        challengeDescription = description,
        challengeCategory = category.name,
        cityName = city,
        weatherCondition = weather,
        challengePoints = points
    )
}
