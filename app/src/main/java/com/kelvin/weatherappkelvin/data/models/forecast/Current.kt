package com.kelvin.weatherappkelvin.data.models.forecast

data class Current(
    val cloud: Double,
    val condition: Condition,
    val feelslike_c: Double,
    val humidity: Double,
    val wind_kph: Double,
    val last_updated: String,
    val temp_c: Double,
)