package com.kelvin.weatherappkelvin.data.models.forecast

data class ForecastWeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)