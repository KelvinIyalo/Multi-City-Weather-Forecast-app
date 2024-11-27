package com.kelvin.weatherappkelvin.data.usecases

import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.data.usecase.UpdateDatabaseUseCase
import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateDatabaseUseCaseTest {
    @Mock
    private lateinit var repository: DatabaseRepository

    private lateinit var updateDatabaseUseCase: UpdateDatabaseUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        updateDatabaseUseCase = UpdateDatabaseUseCase(repository)
    }

    @Test
    fun `test invoke should call repository update with the correct entity`() = runBlocking {
        // Arrange
        val weatherReportEntity = WeatherReportEntity(
            id = 1,
            isFavorite = true,
            locationName = "Test Location",
            locationCountry = "Test Country",
            locationTime = "2024-11-26",
            locationCondition = "Sunny",
            temp_c = 25.0,
            humidity = 60.0,
            wind = 5.0,
            forecastday = emptyList()
        )

        // Act
        updateDatabaseUseCase.invoke(weatherReportEntity)

        // Assert
        verify(repository).update(weatherReportEntity)  // Verify that repository.update() is called with the correct entity
        verifyNoMoreInteractions(repository)  // Ensure that no other interactions were made with the repository
    }

    @Test
    fun `test invoke should not throw exception`() = runBlocking {
        // Arrange
        val weatherReportEntity = WeatherReportEntity(
            id = 1,
            isFavorite = true,
            locationName = "Test Location",
            locationCountry = "Test Country",
            locationTime = "2024-11-26",
            locationCondition = "Sunny",
            temp_c = 25.0,
            humidity = 60.0,
            wind = 5.0,
            forecastday = emptyList()
        )

        // Act & Assert
        try {
            updateDatabaseUseCase.invoke(weatherReportEntity)
        } catch (e: Exception) {
            fail("Exception should not be thrown")
        }

        // Verify that update() was called on repository
        verify(repository).update(weatherReportEntity)
        verifyNoMoreInteractions(repository)  // Ensure no other interactions on the repository mock
    }
}