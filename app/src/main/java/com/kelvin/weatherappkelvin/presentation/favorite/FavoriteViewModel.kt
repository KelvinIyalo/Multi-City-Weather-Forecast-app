package com.kelvin.weatherappkelvin.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.data.usecase.DeleteByIdFromDatabaseUseCase
import com.kelvin.weatherappkelvin.data.usecase.GetAllFavoriteFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteFromDatabaseUseCase: GetAllFavoriteFromDatabaseUseCase,
    private val deleteByIdFromDatabaseUseCase: DeleteByIdFromDatabaseUseCase
) : ViewModel() {

    fun getAllFavoriteWeatherReport(): LiveData<List<WeatherReportEntity>> {
        return getAllFavoriteFromDatabaseUseCase()
    }

    fun deleteFromFavoriteById(id:Int){
        viewModelScope.launch {
             deleteByIdFromDatabaseUseCase(id)
        }
    }

}