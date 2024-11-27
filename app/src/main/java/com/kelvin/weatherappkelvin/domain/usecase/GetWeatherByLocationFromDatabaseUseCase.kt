package com.kelvin.weatherappkelvin.domain.usecase

import androidx.lifecycle.LiveData
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetWeatherByLocationFromDatabaseUseCase @Inject constructor(private val repository: DatabaseRepository) {
     operator fun invoke(): LiveData<WeatherReportEntity?> = repository.getWeatherByLocation()
}