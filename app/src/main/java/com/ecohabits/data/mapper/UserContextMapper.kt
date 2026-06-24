package com.ecohabits.data.mapper

import com.ecohabits.data.local.entity.UserContextEntity
import com.ecohabits.domain.model.UserContext

fun UserContext.toEntity(): UserContextEntity {
    return UserContextEntity(
        idUser = 1, // Usamos un ID fijo para mantener solo un contexto (el último)
        cityName = cityName,
        userName = userName,
        weatherCondition = weatherCondition
    )
}

fun UserContextEntity.toDomain(): UserContext {
    return UserContext(
        cityName = cityName,
        userName = userName,
        weatherCondition = weatherCondition
    )
}
