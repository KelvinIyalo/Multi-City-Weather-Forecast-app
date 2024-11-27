package com.kelvin.weatherappkelvin.data.usecase

import com.kelvin.weatherappkelvin.common.handler.RepositoryResponse
import com.kelvin.weatherappkelvin.data.models.forecast.ForecastWeatherResponse
import com.kelvin.weatherappkelvin.domain.repository.ForecastRepository
import javax.inject.Inject

class GetWeatherFromRemoteSourceUseCase @Inject constructor(private val repository: ForecastRepository) {
     suspend operator fun invoke(location:String,days: Int): RepositoryResponse<ForecastWeatherResponse> = repository.getForecastWeather(location, days)
}