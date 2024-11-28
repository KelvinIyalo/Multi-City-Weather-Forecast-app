package com.kelvin.weatherappkelvin.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.common.handler.RepositoryResponse
import com.kelvin.weatherappkelvin.common.handler.UiState
import com.kelvin.weatherappkelvin.common.handler.mapToWeatherReport
import com.kelvin.weatherappkelvin.data.models.forecast.ForecastWeatherResponse
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.domain.usecase.GetWeatherByLocationFromDatabaseUseCase
import com.kelvin.weatherappkelvin.data.usecase.GetWeatherFromRemoteSourceUseCase
import com.kelvin.weatherappkelvin.data.usecase.SaveToDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherFromRemoteSourceUseCase: GetWeatherFromRemoteSourceUseCase,
    private val getWeatherByLocationFromDatabaseUseCase: GetWeatherByLocationFromDatabaseUseCase,
    private val saveToDatabaseUseCase: SaveToDatabaseUseCase
) : ViewModel() {
    private val mergedLiveData = MediatorLiveData<UiState<WeatherReportEntity>>()


    fun getWeather(): LiveData<UiState<WeatherReportEntity>> {

        // Step 1: Observe local data and emit it immediately when it becomes available
       fetchLocalWeatherData()

        // Step 2: Observe remote data when location becomes available
        viewModelScope.launch {

            val locations = Utility.waitForLocation()
            if (locations == null) {
                mergedLiveData.postValue(UiState.DisplayError("please check your device GPS"))
                return@launch
            }

            // Fetch remote data

            val longitude = locations.longitude.toString()
            val latitude = locations.latitude.toString()
            when (val result = getWeatherFromRemoteSourceUseCase("$latitude,$longitude", 7)) {
                is RepositoryResponse.Success -> handleRemoteWeatherSuccess(result.data.mapToWeatherReport())
                is RepositoryResponse.Error, is RepositoryResponse.ApiError -> handleRemoteWeatherError(result)
            }
        }

        return mergedLiveData
    }

    private fun fetchLocalWeatherData() {
        val localData = getWeatherByLocationFromDatabaseUseCase()
        localData.observeForever { weatherEntity ->
            if (weatherEntity != null) {
                mergedLiveData.postValue(UiState.Success(weatherEntity))
            } else {
                mergedLiveData.postValue(UiState.Loading)
            }
        }
    }


    private suspend fun handleRemoteWeatherSuccess(result: WeatherReportEntity) {
        mergedLiveData.postValue(UiState.Success(result))

        // Save to database
        saveToDatabaseUseCase(result.copy(id = 1, isFavorite = false))
    }

    private fun handleRemoteWeatherError(result: RepositoryResponse<ForecastWeatherResponse>) {
        val errorMessage = when (result) {
            is RepositoryResponse.Error -> result.error
            is RepositoryResponse.ApiError -> result.apiError
            else -> "Unknown error occurred"
        }
        mergedLiveData.postValue(UiState.DisplayError(errorMessage))
    }

}