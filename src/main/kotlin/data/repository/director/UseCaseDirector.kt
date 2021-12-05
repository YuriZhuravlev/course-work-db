package data.repository.director

import data.model.Cathedra
import data.model.ScientificDirector
import data.model.ScientificDirectorDetails

interface UseCaseDirector {
    suspend fun getDirectorDetails(id: Long): ScientificDirectorDetails?

    suspend fun insertDirector(director: ScientificDirector): Long
    suspend fun updateDirector(director: ScientificDirector)
    suspend fun deleteDirector(director: ScientificDirector)
    suspend fun getDirectorsByCathedra(cathedra: Cathedra): List<ScientificDirector>
}