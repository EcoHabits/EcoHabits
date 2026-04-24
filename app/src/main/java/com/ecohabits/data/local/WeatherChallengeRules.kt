package com.ecohabits.data.local

fun WeatherCondition.toChallengeTemplates(): List<WeatherChallengeTemplate> =
    WeatherChallengeTemplate.entries.filter { it.weatherCondition == this }

fun WeatherCondition.orUnknown(): WeatherCondition = if (this == WeatherCondition.UNKNOWN) WeatherCondition.UNKNOWN else this

