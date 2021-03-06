package data.repository.director

import data.db.DAO
import data.model.Cathedra
import data.model.ScientificDirector
import data.model.ScientificDirectorDetails

class UseCaseDirectorImpl(private val dao: DAO) : UseCaseDirector {
    override suspend fun getDirectorDetails(id: Long): ScientificDirectorDetails? {
        return dao.getDirectorDetails(id)
    }

    override suspend fun insertDirector(director: ScientificDirector): Long {
        return dao.insertDirector(director)
    }

    override suspend fun updateDirector(director: ScientificDirector) {
        dao.updateDirector(director)
    }

    override suspend fun deleteDirector(director: ScientificDirector) {
        dao.deleteDirector(director)
    }

    override suspend fun getDirectorsByCathedra(cathedra: Cathedra): List<ScientificDirector> {
        return dao.getDirectorsByCathedra(cathedra.id)
    }

    override suspend fun getDirectors(): List<ScientificDirector> {
        return dao.getDirectors()
    }
}