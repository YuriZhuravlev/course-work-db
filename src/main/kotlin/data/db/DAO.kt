package data.db

import data.model.*

interface DAO {
    fun getCategories(): List<Category>
    fun getCathedras(): List<Cathedra>
    fun getCouncils(): List<ScientificCouncil>
    fun getPublicationsByDirector(id: Long): List<ScientificPublication>
    fun getPublicationsByPostGraduate(id: Long): List<ScientificPublication>

    fun getPostGraduatesByCategory(id: Long): List<PostGraduate>
    fun getPostGraduatesByCathedra(id: Long): List<PostGraduate>
    fun getPostGraduatesByDirector(id: Long): List<PostGraduate>

    fun getPostGraduateDetails(id: Long): PostGraduateDetails?
    fun getDirectorDetails(id: Long): ScientificDirectorDetails?
}
