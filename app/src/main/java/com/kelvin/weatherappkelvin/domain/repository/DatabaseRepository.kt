package com.kelvin.weatherappkelvin.domain.repository

import androidx.lifecycle.LiveData
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity

interface DatabaseRepository {

    suspend fun saveToDb(entity: WeatherReportEntity): Long

    suspend fun update(entity: WeatherReportEntity)

    fun getWeatherByLocation():LiveData<WeatherReportEntity?>

    fun getAllFavorite(): LiveData<List<WeatherReportEntity>>

    suspend fun deleteAllFromDb()

    suspend fun deleteFavoriteById(id: Int)

}