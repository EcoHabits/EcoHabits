package com.ecohabits.domain.model

data class Challenge (
    val idChallenge: Int,
    val titleChallenge: String,
    val descriptionChallenge: String,
    val challengeStatus: Boolean,
    val pointsChallenge: Int
)