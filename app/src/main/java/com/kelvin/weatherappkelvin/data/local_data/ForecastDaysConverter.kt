package com.kelvin.weatherappkelvin.data.local_data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelvin.weatherappkelvin.data.models.weather.WeatherForecastDays

class ForecastDaysConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromForecastDaysList(value: List<WeatherForecastDays>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toForecastDaysList(value: String): List<WeatherForecastDays> {
        val listType = object : TypeToken<List<WeatherForecastDays>>() {}.type
        return gson.fromJson(value, listType)
    }
}