package data.repository

import data.model.Diploma
import data.model.Protection
import data.model.ScientificCouncil

interface UseCaseCouncil {
    fun getCouncils(): List<ScientificCouncil>

    fun insertDiploma(diploma: Diploma): Long
    fun updateDiploma(diploma: Diploma)
    fun deleteDiploma(diploma: Diploma)

    fun insertProtection(protection: Protection): Long
    fun updateProtection(protection: Protection)
    fun deleteProtection(protection: Protection)

    fun insertCouncil(council: ScientificCouncil): Long
    fun updateCouncil(council: ScientificCouncil)
    fun deleteCouncil(council: ScientificCouncil)
}