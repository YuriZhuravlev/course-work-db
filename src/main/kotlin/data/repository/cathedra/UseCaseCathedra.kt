package data.repository.cathedra

import data.model.Cathedra

interface UseCaseCathedra {
    suspend fun getCathedras(): List<Cathedra>

    suspend fun insertCathedra(cathedra: Cathedra): Long
    suspend fun updateCathedra(cathedra: Cathedra)
    suspend fun deleteCathedra(cathedra: Cathedra)
}