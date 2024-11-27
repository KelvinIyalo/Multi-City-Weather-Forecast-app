package com.kelvin.weatherappkelvin.common

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.kelvin.weatherappkelvin.data.usecase.GetAllFavoriteFromDatabaseUseCase
import com.kelvin.weatherappkelvin.data.usecase.GetWeatherFromRemoteSourceUseCase
import com.kelvin.weatherappkelvin.data.usecase.UpdateDatabaseUseCase
import com.kelvin.weatherappkelvin.data.worker.WeatherWorker
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    private val getWeatherFromRemoteSourceUseCase: GetWeatherFromRemoteSourceUseCase,
    private val getAllFavoriteFromDatabaseUseCase: GetAllFavoriteFromDatabaseUseCase,
    private val updateDatabaseUseCase: UpdateDatabaseUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = WeatherWorker(
        appContext,
        workerParameters,
        getWeatherFromRemoteSourceUseCase,
        getAllFavoriteFromDatabaseUseCase,
        updateDatabaseUseCase
    )
}