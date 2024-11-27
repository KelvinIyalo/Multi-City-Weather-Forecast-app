package com.kelvin.weatherappkelvin.data.remoteData

import com.kelvin.weatherappkelvin.data.models.forecast.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastWeatherApiService {

    @GET("v1/forecast.json")
    suspend fun getForecastWeatherService(
        @Query("q") location:String,
        @Query("days") days:Int,
        @Query("key") apiKey:String
    ): Response<ForecastWeatherResponse>
}