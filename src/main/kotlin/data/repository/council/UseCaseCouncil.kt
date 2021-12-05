package data.repository.council

import data.model.Diploma
import data.model.Protection
import data.model.ScientificCouncil

interface UseCaseCouncil {
    suspend fun getCouncils(): List<ScientificCouncil>

    suspend fun insertDiploma(diploma: Diploma): Long
    suspend fun updateDiploma(diploma: Diploma)
    suspend fun deleteDiploma(diploma: Diploma)

    suspend fun insertProtection(protection: Protection): Long
    suspend fun updateProtection(protection: Protection)
    suspend fun deleteProtection(protection: Protection)

    suspend fun insertCouncil(council: ScientificCouncil): Long
    suspend fun updateCouncil(council: ScientificCouncil)
    suspend fun deleteCouncil(council: ScientificCouncil)
}