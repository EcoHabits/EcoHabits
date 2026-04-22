package com.ecohabits.domain.model

data class UserProgress(
    val points: Int,
    val level: Int,
    val metrics: ImpactMetrics,
    val badges: List<Badge>
)

