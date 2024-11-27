package com.kelvin.weatherappkelvin.data.di

import android.content.Context
import androidx.room.Room
import com.kelvin.weatherappkelvin.BuildConfig
import com.kelvin.weatherappkelvin.common.NetworkClient
import com.kelvin.weatherappkelvin.data.local_data.DatabaseDao
import com.kelvin.weatherappkelvin.data.local_data.WeatherDatabase
import com.kelvin.weatherappkelvin.data.provider.AppConfigImpl
import com.kelvin.weatherappkelvin.data.remoteData.ForecastWeatherApiService
import com.kelvin.weatherappkelvin.data.repository.DatabaseRepositoryImpl
import com.kelvin.weatherappkelvin.data.repository.ForecastRepositoryImpl
import com.kelvin.weatherappkelvin.domain.provider.AppConfig
import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import com.kelvin.weatherappkelvin.domain.repository.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideAppConfig() = AppConfigImpl() as AppConfig

    @Singleton
    @Provides
    fun providesDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_entity_db").build()

    @Singleton
    @Provides
    fun provideWeatherEntityDao(database: WeatherDatabase) = database.databaseDao()


    @Provides
    fun provideForecastWeatherApiModule(
        networkClient: NetworkClient
    ): ForecastWeatherApiService {
        return networkClient.getApiService(BuildConfig.FORECAST_BASE_URL)
    }

    @Singleton
    @Provides
    fun provideForecastWeatherRepository(apiService: ForecastWeatherApiService) =
        ForecastRepositoryImpl(apiService) as ForecastRepository

    @Provides
    @Singleton
    fun provideDatabaseRepo(databaseDao: DatabaseDao) =
        DatabaseRepositoryImpl(databaseDao) as DatabaseRepository

//    @Binds
//    @IntoMap
//    @WorkerKey(WeatherWorker::class)
//    abstract fun bindWeatherWorker(factory: WeatherWorker.Factory): ChildWorkerFactory
}