package com.ecohabits.data.local

import com.ecohabits.core.model.HabitCategory

enum class WeatherChallengeTemplate(
    val id: String,
    val weatherCondition: WeatherCondition,
    val category: HabitCategory,
    val titleEs: String,
    val messageEs: String
) {
    SUNRISE_WALK(
        id = "sunrise_walk",
        weatherCondition = WeatherCondition.SUNNY,
        category = HabitCategory.MOVILIDAD,
        titleEs = "Camina al aire libre",
        messageEs = "Aprovecha el buen clima para caminar 10 minutos en lugar de usar transporte motorizado."
    ),
    RAINY_REUSE_WATER(
        id = "rainy_reuse_water",
        weatherCondition = WeatherCondition.RAINY,
        category = HabitCategory.AGUA,
        titleEs = "Ahorra agua en casa",
        messageEs = "Si llueve, aprovecha para reducir el consumo de agua en actividades no esenciales."
    ),
    CLOUDY_NATURAL_LIGHT(
        id = "cloudy_natural_light",
        weatherCondition = WeatherCondition.CLOUDY,
        category = HabitCategory.ENERGIA,
        titleEs = "Usa luz natural",
        messageEs = "Mantén apagadas las luces durante el día y aprovecha la luz natural."
    ),
    COLD_WARM_HOME(
        id = "cold_warm_home",
        weatherCondition = WeatherCondition.COLD,
        category = HabitCategory.ENERGIA,
        titleEs = "Optimiza la calefacción",
        messageEs = "Mantén la calefacción en una temperatura eficiente y evita pérdidas de calor."
    ),
    WINDY_REVIEW_RESIDUES(
        id = "windy_review_residues",
        weatherCondition = WeatherCondition.WINDY,
        category = HabitCategory.RESIDUOS,
        titleEs = "Revisa tus residuos",
        messageEs = "Separa correctamente tus residuos y revisa qué puedes reutilizar antes de desechar."
    ),
    HOT_HYDRATE_SENSIBLY(
        id = "hot_hydrate_sensibly",
        weatherCondition = WeatherCondition.HOT,
        category = HabitCategory.AGUA,
        titleEs = "Hidrátate con cuidado",
        messageEs = "Reduce el desperdicio de agua al hidratarte y prioriza hábitos frescos y sostenibles."
    )
}

