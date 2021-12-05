package data.repository.publication

import data.db.DAO
import data.model.ScientificPublication

class UseCasePublicationImpl(private val dao: DAO) : UseCasePublication {
    override suspend fun getPublicationsByDirector(id: Long): List<ScientificPublication> {
        return dao.getPublicationsByDirector(id)
    }

    override suspend fun getPublicationsByPostGraduate(id: Long): List<ScientificPublication> {
        return dao.getPublicationsByPostGraduate(id)
    }

    override suspend fun insertPublication(publication: ScientificPublication): Long {
        return dao.insertPublication(publication)
    }

    override suspend fun updatePublication(publication: ScientificPublication) {
        dao.updatePublication(publication)
    }

    override suspend fun deletePublication(publication: ScientificPublication) {
        dao.deletePublication(publication)
    }

}