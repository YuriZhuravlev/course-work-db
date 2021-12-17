package data.repository.council

import data.db.DAO
import data.model.Diploma
import data.model.Protection
import data.model.ProtectionDetails
import data.model.ScientificCouncil

class UseCaseCouncilImpl(private val dao: DAO) : UseCaseCouncil {
    override suspend fun getCouncils(): List<ScientificCouncil> {
        return dao.getCouncils()
    }

    override suspend fun insertDiploma(diploma: Diploma): Long {
        return dao.insertDiploma(diploma)
    }

    override suspend fun updateDiploma(diploma: Diploma) {
        dao.updateDiploma(diploma)
    }

    override suspend fun deleteDiploma(diploma: Diploma) {
        dao.deleteDiploma(diploma)
    }

    override suspend fun insertProtection(protection: Protection): Long {
        return dao.insertProtection(protection)
    }

    override suspend fun updateProtection(protection: Protection) {
        dao.updateProtection(protection)
    }

    override suspend fun deleteProtection(protection: Protection) {
        dao.deleteProtection(protection)
    }

    override suspend fun insertCouncil(council: ScientificCouncil): Long {
        return dao.insertCouncil(council)
    }

    override suspend fun updateCouncil(council: ScientificCouncil) {
        dao.updateCouncil(council)
    }

    override suspend fun deleteCouncil(council: ScientificCouncil) {
        dao.deleteCouncil(council)
    }

    override suspend fun getProtectionsByCouncil(councilId: Long): List<ProtectionDetails> {
        return dao.getProtectionsByCouncil(councilId)
    }
}