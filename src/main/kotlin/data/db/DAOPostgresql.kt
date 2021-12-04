package data.db

import config.*
import data.db.entity.*
import data.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DAOPostgresql : DAO {
    private val db by lazy {
        val db = Database.connect(DB_URL, "org.postgresql.Driver", DB_USER, DB_PASSWORD)
        transaction(db) {
            addLogger(StdOutSqlLogger)
            arrayOf(
                CategoryTable,
                CathedraTable,
                DiplomaTable,
                PostGraduateTable,
                ProtectionTable,
                RewardTable,
                CouncilTable,
                DirectionTable,
                DirectorTable,
                PublicationTable
            ).forEach {
                if (!SchemaUtils.checkCycle(it))
                    SchemaUtils.create(it)
            }
        }
        db
    }

    private fun <T> transaction(body: Transaction.() -> T): T {
        return transaction(db) {
            addLogger(StdOutSqlLogger)
            body()
        }
    }

    override fun getCategories(): List<Category> {
        return transaction {
            DBCategory.all().map { Category(it.id.value, it.name) }
        }
    }

    override fun getCathedras(): List<Cathedra> {
        return transaction {
            DBCathedra.all().map { Cathedra(it.id.value, it.name) }
        }
    }

    override fun getCouncils(): List<ScientificCouncil> {
        return transaction {
            DBScientificCouncil.all().map { ScientificCouncil(it.id.value, it.name) }
        }
    }

    override fun getPublicationsByDirector(id: Long): List<ScientificPublication> {
        return transaction {
            val list = mutableListOf<ScientificPublication>()
            DBPostGraduate.find { PostGraduateTable.scientificDirectorId eq id }.forEach {
                DBScientificPublication.find { PublicationTable.postGraduateId eq it.id.value }.forEach { publication ->
                    list.add(
                        ScientificPublication(
                            publication.id.value,
                            publication.name,
                            publication.date,
                            publication.postGraduateId
                        )
                    )
                }
            }
            list
        }
    }

    override fun getPublicationsByPostGraduate(id: Long): List<ScientificPublication> {
        return transaction {
            DBScientificPublication.find { PublicationTable.postGraduateId eq id }.map {
                ScientificPublication(it.id.value, it.name, it.date, it.postGraduateId)
            }
        }
    }

    override fun getPostGraduatesByCategory(id: Long): List<PostGraduate> {
        return transaction {
            DBPostGraduate.find { PostGraduateTable.categoryId eq id }.map {
                PostGraduate(
                    it.id.value,
                    it.name,
                    it.surname,
                    it.scientificDirectorId,
                    it.scientificDirectionId,
                    it.categoryId
                )
            }
        }
    }

    override fun getPostGraduatesByCathedra(id: Long): List<PostGraduate> {
        return transaction {
            val list = mutableListOf<PostGraduate>()
            DBScientificDirector.find { DirectorTable.cathedraId eq id }.forEach {
                DBPostGraduate.find { PostGraduateTable.scientificDirectorId eq it.id.value }.forEach { postGraduate ->
                    list.add(
                        PostGraduate(
                            postGraduate.id.value,
                            postGraduate.name,
                            postGraduate.surname,
                            postGraduate.scientificDirectorId,
                            postGraduate.scientificDirectionId,
                            postGraduate.categoryId
                        )
                    )
                }
            }
            list
        }
    }

    override fun getPostGraduatesByDirector(id: Long): List<PostGraduate> {
        return transaction {
            val list = mutableListOf<PostGraduate>()

            DBPostGraduate.find { PostGraduateTable.scientificDirectorId eq id }.forEach { postGraduate ->
                list.add(
                    PostGraduate(
                        postGraduate.id.value,
                        postGraduate.name,
                        postGraduate.surname,
                        postGraduate.scientificDirectorId,
                        postGraduate.scientificDirectionId,
                        postGraduate.categoryId
                    )
                )
            }
            list
        }
    }

    override fun getPostGraduateDetails(id: Long): PostGraduateDetails? {
        return transaction {
            DBPostGraduate.findById(id)?.let {
                PostGraduateDetails(
                    id = it.id.value,
                    name = it.name,
                    surname = it.surname,
                    scientificDirector = getDirectorDetails(it.scientificDirectorId),
                    scientificDirection = DBScientificDirection.findById(it.scientificDirectionId)?.let { direction ->
                        ScientificDirection(
                            direction.id.value,
                            direction.name
                        )
                    },
                    category = DBCategory.findById(it.categoryId)?.let { category ->
                        Category(
                            category.id.value,
                            category.name
                        )
                    }
                )
            }
        }
    }

    override fun getDirectorDetails(id: Long): ScientificDirectorDetails? {
        return transaction {
            DBScientificDirector.findById(id)?.let {
                ScientificDirectorDetails(
                    id = it.id.value,
                    name = it.name,
                    surname = it.surname,
                    cathedra = DBCathedra.findById(it.cathedraId)?.let { cathedra ->
                        Cathedra(
                            cathedra.id.value,
                            cathedra.name
                        )
                    }
                )
            }
        }
    }
}
