package com.kelvin.weatherappkelvin.data.local_data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity

@Database(entities = [WeatherReportEntity::class], version = 1)
@TypeConverters(ForecastDaysConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

}