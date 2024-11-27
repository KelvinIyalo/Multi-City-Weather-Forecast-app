package com.kelvin.weatherappkelvin.data.usecase

import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveToDatabaseUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(
        entity: WeatherReportEntity
    ): Long = repository.saveToDb(entity)
}