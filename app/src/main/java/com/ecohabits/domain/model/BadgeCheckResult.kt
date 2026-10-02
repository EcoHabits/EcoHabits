package com.ecohabits.domain.model

data class BadgeCheckResult(
    val badges: List<Badge>,
    val newlyUnlockedBadges: List<Badge>
)