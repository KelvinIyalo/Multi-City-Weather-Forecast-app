package com.kelvin.weatherappkelvin.data.local_data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeatherReportEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: WeatherReportEntity)

    @Query("DELETE FROM weather_report")
    suspend fun deleteAll()

    @Query("DELETE FROM weather_report WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)


    @Query("""SELECT * FROM weather_report WHERE  isFavorite = 1 ORDER BY id ASC""")
    fun getFavoriteList(): LiveData<List<WeatherReportEntity>>

    @Query("""SELECT * FROM weather_report WHERE  isFavorite = 0""")
    fun getWeatherByLocation(): LiveData<WeatherReportEntity?>
}