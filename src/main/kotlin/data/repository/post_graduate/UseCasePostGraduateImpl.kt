package data.repository.post_graduate

import data.db.DAO
import data.model.*

class UseCasePostGraduateImpl(private val dao: DAO) : UseCasePostGraduate {
    override suspend fun getCategories(): List<Category> {
        return dao.getCategories()
    }

    override suspend fun getPostGraduatesByCategory(id: Long): List<PostGraduate> {
        return dao.getPostGraduatesByCategory(id)
    }

    override suspend fun getPostGraduatesByCathedra(id: Long): List<PostGraduate> {
        return dao.getPostGraduatesByCathedra(id)
    }

    override suspend fun getPostGraduatesByDirector(id: Long): List<PostGraduate> {
        return dao.getPostGraduatesByDirector(id)
    }

    override suspend fun getPostGraduateDetails(id: Long): PostGraduateDetails? {
        return dao.getPostGraduateDetails(id)
    }

    override suspend fun getRewardsByPostGraduate(id: Long): List<Reward> {
        return dao.getRewardsByPostGraduate(id)
    }

    override suspend fun getDiplomasByPostGraduate(id: Long): List<Diploma> {
        return dao.getDiplomasByPostGraduate(id)
    }

    override suspend fun insertPostGraduate(postGraduate: PostGraduate): Long {
        return dao.insertPostGraduate(postGraduate)
    }

    override suspend fun updatePostGraduate(postGraduate: PostGraduate) {
        dao.updatePostGraduate(postGraduate)
    }

    override suspend fun deletePostGraduate(postGraduate: PostGraduate) {
        dao.deletePostGraduate(postGraduate)
    }

    override suspend fun insertCategory(category: Category): Long {
        return dao.insertCategory(category)
    }

    override suspend fun updateCategory(category: Category) {
        dao.updateCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        return dao.deleteCategory(category)
    }

    override suspend fun insertDirection(direction: ScientificDirection): Long {
        return dao.insertDirection(direction)
    }

    override suspend fun updateDirection(direction: ScientificDirection) {
        dao.updateDirection(direction)
    }

    override suspend fun deleteDirection(direction: ScientificDirection) {
        dao.deleteDirection(direction)
    }

    override suspend fun insertReward(reward: Reward): Long {
        return dao.insertReward(reward)
    }

    override suspend fun updateReward(reward: Reward) {
        dao.updateReward(reward)
    }

    override suspend fun deleteReward(reward: Reward) {
        dao.deleteReward(reward)
    }
}