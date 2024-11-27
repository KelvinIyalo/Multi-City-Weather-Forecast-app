package com.kelvin.weatherappkelvin.data.repository

import com.kelvin.weatherappkelvin.BuildConfig
import com.kelvin.weatherappkelvin.common.handler.RepositoryResponse
import com.kelvin.weatherappkelvin.common.handler.exceptionHandler
import com.kelvin.weatherappkelvin.common.handler.getCallResponseState
import com.kelvin.weatherappkelvin.data.models.forecast.ForecastWeatherResponse
import com.kelvin.weatherappkelvin.data.remoteData.ForecastWeatherApiService
import com.kelvin.weatherappkelvin.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(private val apiService: ForecastWeatherApiService):ForecastRepository {

    override suspend fun getForecastWeather(
        location:String,
        days:Int
    ): RepositoryResponse<ForecastWeatherResponse> {
        return try {
            val apiKey = BuildConfig.FORECAST_KEY
            val result = apiService.getForecastWeatherService(location,days,apiKey)
            getCallResponseState(result)
        } catch (exception: Exception) {
            return exceptionHandler(exception)
        }
    }
}