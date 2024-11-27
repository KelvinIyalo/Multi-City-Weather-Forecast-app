package com.kelvin.weatherappkelvin.data.models.forecast

data class Day(
    val avghumidity: Double,
    val avgtemp_c: Double,
    val condition: Condition
)