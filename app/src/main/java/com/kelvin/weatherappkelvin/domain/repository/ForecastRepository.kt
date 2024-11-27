package com.kelvin.weatherappkelvin.domain.repository

import com.kelvin.weatherappkelvin.common.handler.RepositoryResponse
import com.kelvin.weatherappkelvin.data.models.forecast.ForecastWeatherResponse

interface ForecastRepository {
    suspend fun getForecastWeather(location:String, days:Int): RepositoryResponse<ForecastWeatherResponse>

}