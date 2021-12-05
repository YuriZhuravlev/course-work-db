package data.repository

import data.model.Cathedra

interface UseCaseCathedra {
    fun getCathedras(): List<Cathedra>

    fun insertCathedra(cathedra: Cathedra): Long
    fun updateCathedra(cathedra: Cathedra)
    fun deleteCathedra(cathedra: Cathedra)
}