package com.ecohabits.domain.model

data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val code: String,
    val ruleType: String,
    val ruleValue: Int,
    val unlocked: Boolean = false,
    val unlockedDate: String? = null
)
