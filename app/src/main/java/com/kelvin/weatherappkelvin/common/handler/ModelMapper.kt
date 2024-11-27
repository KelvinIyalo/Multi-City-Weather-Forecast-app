package com.kelvin.weatherappkelvin.common.handler

import com.kelvin.weatherappkelvin.data.models.forecast.ForecastWeatherResponse
import com.kelvin.weatherappkelvin.data.models.forecast.Forecastday
import com.kelvin.weatherappkelvin.data.models.weather.WeatherForecastDays
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity


fun ForecastWeatherResponse.mapToWeatherReport():WeatherReportEntity{
    return WeatherReportEntity(
        locationName = this.location.name,
        locationCountry = this.location.country,
        locationTime = this.current.last_updated,
        humidity = this.current.humidity,
        wind = this.current.wind_kph,
        locationCondition = this.current.condition.text,
        temp_c = this.current.temp_c,
        forecastday = this.forecast.forecastday.map { it.mapToWeatherForecastDays() }
    )
}

fun Forecastday.mapToWeatherForecastDays():WeatherForecastDays{
    return WeatherForecastDays(
        forecastDay = this.date,
        condition = this.day.condition.text,
        avgtemp_c =this.day.avgtemp_c,
        avghumidity = this.day.avghumidity
    )
}