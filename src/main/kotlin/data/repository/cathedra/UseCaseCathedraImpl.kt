package data.repository.cathedra

import data.db.DAO
import data.model.Cathedra

class UseCaseCathedraImpl(private val dao: DAO) : UseCaseCathedra {
    override suspend fun getCathedras(): List<Cathedra> {
        return dao.getCathedras()
    }

    override suspend fun insertCathedra(cathedra: Cathedra): Long {
        return dao.insertCathedra(cathedra)
    }

    override suspend fun updateCathedra(cathedra: Cathedra) {
        dao.updateCathedra(cathedra)
    }

    override suspend fun deleteCathedra(cathedra: Cathedra) {
        dao.deleteCathedra(cathedra)
    }
}