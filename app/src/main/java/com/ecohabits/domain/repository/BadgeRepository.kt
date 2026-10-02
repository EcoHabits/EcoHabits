package com.ecohabits.domain.repository

import com.ecohabits.domain.model.Badge

interface BadgeRepository {

    suspend fun getAllBadges(): List<Badge>

    suspend fun getUserBadges(
        userId: String
    ): List<Badge>

    suspend fun unlockBadge(
        userId: String,
        badgeId: String
    ): Boolean
}
