package com.kelvin.weatherappkelvin.data.repository

import androidx.lifecycle.LiveData
import com.kelvin.weatherappkelvin.data.local_data.DatabaseDao
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val databaseDao: DatabaseDao) :
    DatabaseRepository {
    override suspend fun saveToDb(entity: WeatherReportEntity): Long {
        return databaseDao.insert(entity)
    }

    override suspend fun update(entity: WeatherReportEntity) {
        databaseDao.update(entity)
    }

    override fun getWeatherByLocation(): LiveData<WeatherReportEntity?> {
        return databaseDao.getWeatherByLocation()
    }

    override fun getAllFavorite(): LiveData<List<WeatherReportEntity>> {
        return databaseDao.getFavoriteList()
    }

    override suspend fun deleteAllFromDb() {
        databaseDao.deleteAll()
    }

    override suspend fun deleteFavoriteById(id: Int) {
        databaseDao.deleteFavoriteById(id)
    }


}