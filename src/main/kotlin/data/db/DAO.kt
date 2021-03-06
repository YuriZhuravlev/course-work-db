package data.db

import data.db.entity.DBReport
import data.model.*

interface DAO {
    fun getCategories(): List<Category>
    fun getCathedras(): List<Cathedra>
    fun getCouncils(): List<ScientificCouncil>
    fun getDirections(): List<ScientificDirection>
    fun getDirectors(): List<ScientificDirector>

    fun getPublicationsByDirector(id: Long): List<ScientificPublication>
    fun getPublicationsByPostGraduate(id: Long): List<ScientificPublication>
    fun getRewardsByPostGraduate(id: Long): List<Reward>
    fun getDiplomasByPostGraduate(id: Long): List<Diploma>

    fun getDirectorsByCathedra(id: Long): List<ScientificDirector>
    fun getPostGraduatesByCategory(id: Long): List<PostGraduate>
    fun getPostGraduatesByCathedra(id: Long): List<PostGraduate>
    fun getPostGraduatesByDirector(id: Long): List<PostGraduate>

    fun getPostGraduateDetails(id: Long): PostGraduateDetails?
    fun getDirectorDetails(id: Long): ScientificDirectorDetails?

    fun getProtectionsByCouncil(councilId: Long): List<ProtectionDetails>

    fun insertCategory(category: Category): Long
    fun updateCategory(category: Category)
    fun deleteCategory(category: Category)

    fun insertCathedra(cathedra: Cathedra): Long
    fun updateCathedra(cathedra: Cathedra)
    fun deleteCathedra(cathedra: Cathedra)

    fun insertDiploma(diploma: Diploma): Long
    fun updateDiploma(diploma: Diploma)
    fun deleteDiploma(diploma: Diploma)

    fun insertPostGraduate(postGraduate: PostGraduate): Long
    fun updatePostGraduate(postGraduate: PostGraduate)
    fun deletePostGraduate(postGraduate: PostGraduate)

    fun insertProtection(protection: Protection): Long
    fun updateProtection(protection: Protection)
    fun deleteProtection(protection: Protection)

    fun insertReward(reward: Reward): Long
    fun updateReward(reward: Reward)
    fun deleteReward(reward: Reward)

    fun insertCouncil(council: ScientificCouncil): Long
    fun updateCouncil(council: ScientificCouncil)
    fun deleteCouncil(council: ScientificCouncil)

    fun insertDirection(direction: ScientificDirection): Long
    fun updateDirection(direction: ScientificDirection)
    fun deleteDirection(direction: ScientificDirection)

    fun insertDirector(director: ScientificDirector): Long
    fun updateDirector(director: ScientificDirector)
    fun deleteDirector(director: ScientificDirector)

    fun insertPublication(publication: ScientificPublication): Long
    fun updatePublication(publication: ScientificPublication)
    fun deletePublication(publication: ScientificPublication)

    fun getReportsByCategories(): List<DBReport>
    fun getReportsByDirections(): List<DBReport>
    fun getReportsByCathedras(): List<DBReport>
    fun getReportsByDirectors(): List<DBReport>
}
