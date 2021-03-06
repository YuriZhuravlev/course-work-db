package data.db

import config.DB_PASSWORD
import config.DB_URL
import config.DB_USER
import data.db.entity.*
import data.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet

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
            DBCategory.all().orderBy(CategoryTable.id to SortOrder.ASC).map { Category(it.id.value, it.name) }
        }
    }

    override fun getCathedras(): List<Cathedra> {
        return transaction {
            DBCathedra.all().orderBy(CathedraTable.id to SortOrder.ASC).map { Cathedra(it.id.value, it.name) }
        }
    }

    override fun getCouncils(): List<ScientificCouncil> {
        return transaction {
            DBScientificCouncil.all().orderBy(CouncilTable.id to SortOrder.ASC)
                .map { ScientificCouncil(it.id.value, it.name) }
        }
    }

    override fun getDirections(): List<ScientificDirection> {
        return transaction {
            DBScientificDirection.all().orderBy(DirectionTable.id to SortOrder.ASC)
                .map { ScientificDirection(it.id.value, it.name) }
        }
    }

    override fun getDirectors(): List<ScientificDirector> {
        return transaction {
            DBScientificDirector.all().orderBy(DirectorTable.id to SortOrder.ASC)
                .map { ScientificDirector(it.id.value, it.name, it.surname, it.cathedraId) }
        }
    }

    override fun getPublicationsByDirector(id: Long): List<ScientificPublication> {
        return transaction {
            val list = mutableListOf<ScientificPublication>()
            DBPostGraduate.find { PostGraduateTable.scientificDirectorId eq id }.forEach {
                DBScientificPublication.find { PublicationTable.postGraduateId eq it.id.value }
                    .orderBy(PublicationTable.id to SortOrder.ASC).forEach { publication ->
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
            DBScientificPublication.find { PublicationTable.postGraduateId eq id }
                .orderBy(PublicationTable.id to SortOrder.ASC).map {
                    ScientificPublication(it.id.value, it.name, it.date, it.postGraduateId)
                }
        }
    }

    override fun getPostGraduatesByCategory(id: Long): List<PostGraduate> {
        return transaction {
            DBPostGraduate.find { PostGraduateTable.categoryId eq id }.orderBy(PostGraduateTable.id to SortOrder.ASC)
                .map {
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
                DBPostGraduate.find { PostGraduateTable.scientificDirectorId eq it.id.value }
                    .orderBy(PostGraduateTable.id to SortOrder.ASC).forEach { postGraduate ->
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

            DBPostGraduate.find { PostGraduateTable.scientificDirectorId eq id }
                .orderBy(PostGraduateTable.id to SortOrder.ASC).forEach { postGraduate ->
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
                it[name] = category.name.let { if (it.length > 50) it.substring(0, 49) else it }
            }.value
        }
    }

    override fun updateCategory(category: Category) {
        transaction {
            CategoryTable.update({ CategoryTable.id eq category.id }) {
                it[name] = category.name.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = cathedra.name.let { if (it.length > 50) it.substring(0, 49) else it }
            }.value
        }
    }

    override fun updateCathedra(cathedra: Cathedra) {
        transaction {
            CathedraTable.update({ CathedraTable.id eq cathedra.id }) {
                it[name] = cathedra.name.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = diploma.name.let { if (it.length > 50) it.substring(0, 49) else it }
                it[postGraduateId] = diploma.postGraduateId
                it[protectionId] = diploma.protectionId
            }.value
        }
    }

    override fun updateDiploma(diploma: Diploma) {
        transaction {
            DiplomaTable.update({ DiplomaTable.id eq diploma.id }) {
                it[name] = diploma.name.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = postGraduate.name.let { if (it.length > 50) it.substring(0, 49) else it }
                it[categoryId] = postGraduate.categoryId
                it[surname] = postGraduate.surname.let { if (it.length > 50) it.substring(0, 49) else it }
                it[scientificDirectionId] = postGraduate.scientificDirectionId
                it[scientificDirectorId] = postGraduate.scientificDirectorId
            }.value
        }
    }

    override fun updatePostGraduate(postGraduate: PostGraduate) {
        transaction {
            PostGraduateTable.update({ PostGraduateTable.id eq postGraduate.id }) {
                it[name] = postGraduate.name.let { if (it.length > 50) it.substring(0, 49) else it }
                it[categoryId] = postGraduate.categoryId
                it[surname] = postGraduate.surname.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = reward.name.let { if (it.length > 50) it.substring(0, 49) else it }
                it[date] = reward.date
                it[postGraduateId] = reward.postGraduateId
            }.value
        }
    }

    override fun updateReward(reward: Reward) {
        transaction {
            RewardTable.update({ RewardTable.id eq reward.id }) {
                it[name] = reward.name.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = council.name.let { if (it.length > 50) it.substring(0, 49) else it }
            }.value
        }
    }

    override fun updateCouncil(council: ScientificCouncil) {
        transaction {
            CouncilTable.update({ CouncilTable.id eq council.id }) {
                it[name] = council.name.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = direction.name.let { if (it.length > 50) it.substring(0, 49) else it }
            }.value
        }
    }

    override fun updateDirection(direction: ScientificDirection) {
        transaction {
            DirectionTable.update({ DirectionTable.id eq direction.id }) {
                it[name] = direction.name.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = director.name.let { if (it.length > 50) it.substring(0, 49) else it }
                it[surname] = director.surname.let { if (it.length > 50) it.substring(0, 49) else it }
                it[cathedraId] = director.cathedraId
            }.value
        }
    }

    override fun updateDirector(director: ScientificDirector) {
        transaction {
            DirectorTable.update({ DirectorTable.id eq director.id }) {
                it[name] = director.name.let { if (it.length > 50) it.substring(0, 49) else it }
                it[surname] = director.surname.let { if (it.length > 50) it.substring(0, 49) else it }
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
                it[name] = publication.name.let { if (it.length > 50) it.substring(0, 49) else it }
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
            DBReward.find { RewardTable.postGraduateId eq id }.orderBy(RewardTable.id to SortOrder.ASC).map {
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
            DBDiploma.find { DiplomaTable.postGraduateId eq id }.orderBy(DiplomaTable.id to SortOrder.ASC).map {
                Diploma(
                    it.id.value,
                    it.name,
                    it.postGraduateId,
                    it.protectionId
                )
            }
        }
    }

    override fun getDirectorsByCathedra(id: Long): List<ScientificDirector> {
        return transaction {
            DBScientificDirector.find { DirectorTable.cathedraId eq id }.orderBy(DirectorTable.id to SortOrder.ASC)
                .map {
                    ScientificDirector(
                        it.id.value,
                        it.name,
                        it.surname,
                        it.cathedraId
                    )
                }
        }
    }

    override fun getProtectionsByCouncil(councilId: Long): List<ProtectionDetails> {
        return transaction {
            val list = mutableListOf<ProtectionDetails>()
            DBProtection.find { ProtectionTable.councilId eq councilId }.forEach {
                var find = false
                DBDiploma.find { DiplomaTable.protectionId eq it.id.value }.forEach { diploma ->
                    find = true
                    list.add(
                        ProtectionDetails(
                            it.id.value,
                            it.councilId,
                            it.date,
                            Diploma(
                                diploma.id.value,
                                diploma.name,
                                diploma.postGraduateId,
                                diploma.protectionId
                            )
                        )
                    )
                }
                if (!find)
                    list.add(ProtectionDetails(it.id.value, it.councilId, it.date, null))
            }
            list
        }
    }

    override fun getReportsByCategories(): List<DBReport> {
        var reports: List<DBReport>? = null
        transaction {
            exec("SELECT * FROM ReportsByCategories();") {
                reports = it.parseReport()
            }
        }
        return reports.orEmpty()
    }

    override fun getReportsByDirections(): List<DBReport> {
        var reports: List<DBReport>? = null
        transaction {
            exec("SELECT * FROM ReportsByDirections();") {
                reports = it.parseReport()
            }
        }
        return reports.orEmpty()
    }

    override fun getReportsByCathedras(): List<DBReport> {
        var reports: List<DBReport>? = null
        transaction {
            exec("SELECT * FROM ReportsByCathedras();") {
                reports = it.parseReport()
            }
        }
        return reports.orEmpty()
    }

    override fun getReportsByDirectors(): List<DBReport> {
        var reports: List<DBReport>? = null
        transaction {
            exec("SELECT * FROM ReportsByDirectors();") {
                reports = it.parseReport()
            }
        }
        return reports.orEmpty()
    }

    private fun ResultSet.parseReport(): List<DBReport> {
        val list = mutableListOf<DBReport>()
        while (this.next()) {
            list.add(DBReport(this.getString(DBReport.NAME), this.getInt(DBReport.COUNT)))
        }
        return list
    }
}
