package data.repository.publication

import data.model.ScientificPublication

interface UseCasePublication {
    suspend fun getPublicationsByDirector(id: Long): List<ScientificPublication>
    suspend fun getPublicationsByPostGraduate(id: Long): List<ScientificPublication>

    suspend fun insertPublication(publication: ScientificPublication): Long
    suspend fun updatePublication(publication: ScientificPublication)
    suspend fun deletePublication(publication: ScientificPublication)
}