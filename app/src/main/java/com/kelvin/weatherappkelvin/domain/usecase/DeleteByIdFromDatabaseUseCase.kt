package com.kelvin.weatherappkelvin.data.usecase

import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteByIdFromDatabaseUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(id:Int): Unit = repository.deleteFavoriteById(id)
}