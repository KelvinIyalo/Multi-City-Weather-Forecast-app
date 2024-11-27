package com.kelvin.weatherappkelvin.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kelvin.weatherappkelvin.common.awaitValue
import com.kelvin.weatherappkelvin.common.handler.RepositoryResponse
import com.kelvin.weatherappkelvin.common.handler.mapToWeatherReport
import com.kelvin.weatherappkelvin.data.usecase.GetAllFavoriteFromDatabaseUseCase
import com.kelvin.weatherappkelvin.data.usecase.GetWeatherFromRemoteSourceUseCase
import com.kelvin.weatherappkelvin.data.usecase.UpdateDatabaseUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @Assisted private val getWeatherFromRemoteSourceUseCase: GetWeatherFromRemoteSourceUseCase,
    @Assisted private val getAllFavoriteFromDatabaseUseCase: GetAllFavoriteFromDatabaseUseCase,
    @Assisted private val updateDatabaseUseCase: UpdateDatabaseUseCase
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        Log.d("XXX TAG WORKER", "WORKER")
        return try {
            val favoriteItems = getAllFavoriteFromDatabaseUseCase.invoke().awaitValue()
            favoriteItems.forEach { favorite ->
                Log.d("XXX TAG WORKER", favorite.toString())
                fetchWeatherAndUpdateDatabase(favorite.locationName,7,favorite.id)
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private suspend fun fetchWeatherAndUpdateDatabase(name: String,days:Int,itemId:Int) {
        when (val result = getWeatherFromRemoteSourceUseCase(name,days)) {
            is RepositoryResponse.Success -> {
                val response = result.data.mapToWeatherReport()
                updateDatabaseUseCase(entity = response.copy(id =itemId))
            }

            is RepositoryResponse.Error, is RepositoryResponse.ApiError -> {
               Log.d("TAG WORKMAN",result.toString())
            }
        }
    }
}