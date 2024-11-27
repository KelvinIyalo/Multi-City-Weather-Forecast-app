package com.kelvin.weatherappkelvin.data.models.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kelvin.weatherappkelvin.data.local_data.ForecastDaysConverter

@Entity(tableName = "weather_report")
data class WeatherReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isFavorite: Boolean = true,
    val locationName:String,
    val locationCountry:String,
    val locationTime:String,
    val locationCondition:String,
    val temp_c:Double,
    val humidity:Double,
    val wind:Double,
    @TypeConverters(ForecastDaysConverter::class)
    val forecastday: List<WeatherForecastDays>
):java.io.Serializable
