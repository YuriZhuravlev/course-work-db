package data.repository.director

import data.model.ScientificDirector
import data.model.ScientificDirectorDetails

interface UseCaseDirector {
    suspend fun getDirectorDetails(id: Long): ScientificDirectorDetails?

    suspend fun insertDirector(director: ScientificDirector): Long
    suspend fun updateDirector(director: ScientificDirector)
    suspend fun deleteDirector(director: ScientificDirector)
}