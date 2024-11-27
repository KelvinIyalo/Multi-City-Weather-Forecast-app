package com.kelvin.weatherappkelvin.data.usecases

import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.data.usecase.SaveToDatabaseUseCase
import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SaveToDatabaseUseCaseTest {

    @Mock
    private lateinit var mockRepository: DatabaseRepository

    private lateinit var saveToDatabaseUseCase: SaveToDatabaseUseCase

    @Before
    fun setUp() {
        saveToDatabaseUseCase = SaveToDatabaseUseCase(mockRepository)
    }

    @Test
    fun `invoke should call repository and return id`() = runBlocking {
        // Arrange
        val weatherReport = WeatherReportEntity(
            locationName = "TestLocation",
            locationCountry = "TestCountry",
            locationTime = "2024-11-26 12:00:00",
            locationCondition = "Sunny",
            temp_c = 25.0,
            humidity = 50.0,
            wind = 10.0,
            forecastday = emptyList()
        )
        whenever(mockRepository.saveToDb(weatherReport)).thenReturn(1L)

        // Act
        val result = saveToDatabaseUseCase(weatherReport)

        // Assert
        verify(mockRepository).saveToDb(weatherReport)
        assertEquals(1L, result)
    }
}