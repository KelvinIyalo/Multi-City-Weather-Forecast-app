package com.kelvin.weatherappkelvin.data.usecase

import com.kelvin.weatherappkelvin.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteAllFromDatabaseUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(): Unit = repository.deleteAllFromDb()
}