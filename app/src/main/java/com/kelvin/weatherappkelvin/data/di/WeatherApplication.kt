package com.kelvin.weatherappkelvin.data.di

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kelvin.weatherappkelvin.common.CustomWorkerFactory
import com.kelvin.weatherappkelvin.common.Permissions
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.data.worker.WeatherWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var customWorkerFactory: CustomWorkerFactory

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        if (Permissions.hasPermission(this)) {
            Permissions.requestLocation(this.applicationContext)
        }
        setupWorkManager()
    }

    private fun setupWorkManager() {
        val workRequest = PeriodicWorkRequestBuilder<WeatherWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "WeatherWorker",
            ExistingPeriodicWorkPolicy.KEEP, // Prevent duplicate work
            workRequest
        )
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(customWorkerFactory)
            .build()
    }
}