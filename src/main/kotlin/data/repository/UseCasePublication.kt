package data.repository

import data.model.ScientificPublication

interface UseCasePublication {
    fun getPublicationsByDirector(id: Long): List<ScientificPublication>
    fun getPublicationsByPostGraduate(id: Long): List<ScientificPublication>

    fun insertPublication(publication: ScientificPublication): Long
    fun updatePublication(publication: ScientificPublication)
    fun deletePublication(publication: ScientificPublication)
}