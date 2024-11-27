package com.kelvin.weatherappkelvin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kelvin.weatherappkelvin.common.handler.RepositoryResponse
import com.kelvin.weatherappkelvin.common.handler.UiState
import com.kelvin.weatherappkelvin.common.handler.mapToWeatherReport
import com.kelvin.weatherappkelvin.data.models.forecast.ForecastWeatherResponse
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.data.usecase.GetWeatherFromRemoteSourceUseCase
import com.kelvin.weatherappkelvin.data.usecase.SaveToDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getWeatherFromRemoteSourceUseCase: GetWeatherFromRemoteSourceUseCase,
    private val saveToDatabaseUseCase: SaveToDatabaseUseCase
) : ViewModel() {

    /**
     * Searches for weather data by location name and emits UiState.
     * Utilizes liveData for coroutine-scoped data emissions.
     */
    fun searchWeatherByName(name: String) = liveData {
        emit(UiState.Loading) // Emit loading state

        val response = getWeatherFromRemoteSourceUseCase(name, 7)
        emit(handleSearchResponse(response))
    }

    /**
     * Saves a WeatherReportEntity as a favorite into the database.
     */
    fun saveFavorite(entity: WeatherReportEntity) {
        viewModelScope.launch {
            saveToDatabaseUseCase(entity)
        }
    }

    /**
     * Handles the result of the weather search, converting it into a UiState.
     */
    private fun handleSearchResponse(response:  RepositoryResponse<ForecastWeatherResponse>): UiState<WeatherReportEntity> {
        return when (response) {
            is RepositoryResponse.Success -> {
                UiState.Success(response.data.mapToWeatherReport())
            }
            is RepositoryResponse.Error -> {
                UiState.DisplayError(response.error)
            }
            is RepositoryResponse.ApiError -> {
                UiState.DisplayError(response.apiError)
            }
        }
    }

}