package data.db

import config.DB_PASSWORD
import config.DB_URL
import config.DB_USER
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

    override fun insertCategory(category: Category): Long {
        return transaction {
            CategoryTable.insertAndGetId {
                it[name] = category.name
            }.value
        }
    }

    override fun updateCategory(category: Category) {
        transaction {
            CategoryTable.update({ CategoryTable.id eq category.id }) {
                it[name] = category.name
            }
        }
    }

    override fun deleteCategory(category: Category) {
        transaction {
            CategoryTable.deleteWhere { CategoryTable.id eq category.id }
        }
    }

    override fun insertCathedra(cathedra: Cathedra): Long {
        return transaction {
            CathedraTable.insertAndGetId {
                it[name] = cathedra.name
            }.value
        }
    }

    override fun updateCathedra(cathedra: Cathedra) {
        transaction {
            CathedraTable.update({ CathedraTable.id eq cathedra.id }) {
                it[name] = cathedra.name
            }
        }
    }

    override fun deleteCathedra(cathedra: Cathedra) {
        transaction {
            CathedraTable.deleteWhere { CathedraTable.id eq cathedra.id }
        }
    }

    override fun insertDiploma(diploma: Diploma): Long {
        return transaction {
            DiplomaTable.insertAndGetId {
                it[name] = diploma.name
                it[postGraduateId] = diploma.postGraduateId
                it[protectionId] = diploma.protectionId
            }.value
        }
    }

    override fun updateDiploma(diploma: Diploma) {
        transaction {
            DiplomaTable.update({ DiplomaTable.id eq diploma.id }) {
                it[name] = diploma.name
                it[postGraduateId] = diploma.postGraduateId
                it[protectionId] = diploma.protectionId
            }
        }
    }

    override fun deleteDiploma(diploma: Diploma) {
        transaction {
            DiplomaTable.deleteWhere { DiplomaTable.id eq diploma.id }
        }
    }

    override fun insertPostGraduate(postGraduate: PostGraduate): Long {
        return transaction {
            PostGraduateTable.insertAndGetId {
                it[name] = postGraduate.name
                it[categoryId] = postGraduate.categoryId
                it[surname] = postGraduate.surname
                it[scientificDirectionId] = postGraduate.scientificDirectionId
                it[scientificDirectorId] = postGraduate.scientificDirectorId
            }.value
        }
    }

    override fun updatePostGraduate(postGraduate: PostGraduate) {
        transaction {
            PostGraduateTable.update({ PostGraduateTable.id eq postGraduate.id }) {
                it[name] = postGraduate.name
                it[categoryId] = postGraduate.categoryId
                it[surname] = postGraduate.surname
                it[scientificDirectionId] = postGraduate.scientificDirectionId
                it[scientificDirectorId] = postGraduate.scientificDirectorId
            }
        }
    }

    override fun deletePostGraduate(postGraduate: PostGraduate) {
        transaction {
            PostGraduateTable.deleteWhere { PostGraduateTable.id eq postGraduate.id }
        }
    }

    override fun insertProtection(protection: Protection): Long {
        return transaction {
            ProtectionTable.insertAndGetId {
                it[date] = protection.date
                it[councilId] = protection.councilId
            }.value
        }
    }

    override fun updateProtection(protection: Protection) {
        transaction {
            ProtectionTable.update({ ProtectionTable.id eq protection.id }) {
                it[date] = protection.date
                it[councilId] = protection.councilId
            }
        }
    }

    override fun deleteProtection(protection: Protection) {
        transaction {
            ProtectionTable.deleteWhere { ProtectionTable.id eq protection.id }
        }
    }

    override fun insertReward(reward: Reward): Long {
        return transaction {
            RewardTable.insertAndGetId {
                it[name] = reward.name
                it[date] = reward.date
                it[postGraduateId] = reward.postGraduateId
            }.value
        }
    }

    override fun updateReward(reward: Reward) {
        transaction {
            RewardTable.update({ RewardTable.id eq reward.id }) {
                it[name] = reward.name
                it[date] = reward.date
                it[postGraduateId] = reward.postGraduateId
            }
        }
    }

    override fun deleteReward(reward: Reward) {
        transaction {
            RewardTable.deleteWhere { RewardTable.id eq reward.id }
        }
    }

    override fun insertCouncil(council: ScientificCouncil): Long {
        return transaction {
            CouncilTable.insertAndGetId {
                it[name] = council.name
            }.value
        }
    }

    override fun updateCouncil(council: ScientificCouncil) {
        transaction {
            CouncilTable.update({ CouncilTable.id eq council.id }) {
                it[name] = council.name
            }
        }
    }

    override fun deleteCouncil(council: ScientificCouncil) {
        transaction {
            CouncilTable.deleteWhere { CouncilTable.id eq council.id }
        }
    }

    override fun insertDirection(direction: ScientificDirection): Long {
        return transaction {
            DirectionTable.insertAndGetId {
                it[name] = direction.name
            }.value
        }
    }

    override fun updateDirection(direction: ScientificDirection) {
        transaction {
            DirectionTable.update({ DirectionTable.id eq direction.id }) {
                it[name] = direction.name
            }
        }
    }

    override fun deleteDirection(direction: ScientificDirection) {
        transaction {
            DirectionTable.deleteWhere { DirectionTable.id eq direction.id }
        }
    }

    override fun insertDirector(director: ScientificDirector): Long {
        return transaction {
            DirectorTable.insertAndGetId {
                it[name] = director.name
                it[surname] = director.surname
                it[cathedraId] = director.cathedraId
            }.value
        }
    }

    override fun updateDirector(director: ScientificDirector) {
        transaction {
            DirectorTable.update({ DirectorTable.id eq director.id }) {
                it[name] = director.name
                it[surname] = director.surname
                it[cathedraId] = director.cathedraId
            }
        }
    }

    override fun deleteDirector(director: ScientificDirector) {
        transaction {
            DirectorTable.deleteWhere { DirectorTable.id eq director.id }
        }
    }

    override fun insertPublication(publication: ScientificPublication): Long {
        return transaction {
            PublicationTable.insertAndGetId {
                it[name] = publication.name
                it[date] = publication.date
                it[postGraduateId] = publication.postGraduateId
            }.value
        }
    }

    override fun updatePublication(publication: ScientificPublication) {
        transaction {
            PublicationTable.update({ PublicationTable.id eq publication.id }) {
                it[name] = publication.name
                it[date] = publication.date
                it[postGraduateId] = publication.postGraduateId
            }
        }
    }

    override fun deletePublication(publication: ScientificPublication) {
        transaction {
            PublicationTable.deleteWhere { PublicationTable.id eq publication.id }
        }
    }

    override fun getRewardsByPostGraduate(id: Long): List<Reward> {
        return transaction {
            DBReward.find { RewardTable.postGraduateId eq id }.map {
                Reward(
                    it.id.value,
                    it.name,
                    it.date,
                    it.postGraduateId
                )
            }
        }
    }

    override fun getDiplomasByPostGraduate(id: Long): List<Diploma> {
        return transaction {
            DBDiploma.find { DiplomaTable.postGraduateId eq id }.map {
                Diploma(
                    it.id.value,
                    it.name,
                    it.postGraduateId,
                    it.protectionId
                )
            }
        }
    }
}
