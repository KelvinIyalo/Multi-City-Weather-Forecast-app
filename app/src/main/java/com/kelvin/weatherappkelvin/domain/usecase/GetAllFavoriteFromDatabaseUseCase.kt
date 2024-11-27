package com.kelvin.weatherappkelvin.data.usecase

import androidx.lifecycle.LiveData
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetAllFavoriteFromDatabaseUseCase @Inject constructor(private val repository: DatabaseRepository) {
     operator fun invoke(): LiveData<List<WeatherReportEntity>> = repository.getAllFavorite()
}