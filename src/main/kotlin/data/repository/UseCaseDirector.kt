package data.repository

import data.model.*

interface UseCaseDirector {
    fun getDirectorDetails(id: Long): ScientificDirectorDetails?

    fun insertDirector(director: ScientificDirector): Long
    fun updateDirector(director: ScientificDirector)
    fun deleteDirector(director: ScientificDirector)
}