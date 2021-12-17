package data.repository.post_graduate

import data.model.*

interface UseCasePostGraduate {

    suspend fun getPostGraduatesByCategory(id: Long): List<PostGraduate>
    suspend fun getPostGraduatesByCathedra(id: Long): List<PostGraduate>
    suspend fun getPostGraduatesByDirector(id: Long): List<PostGraduate>

    suspend fun getPostGraduateDetails(id: Long): PostGraduateDetails?
    suspend fun getRewardsByPostGraduate(id: Long): List<Reward>
    suspend fun getDiplomasByPostGraduate(id: Long): List<Diploma>

    suspend fun insertPostGraduate(postGraduate: PostGraduate): Long
    suspend fun updatePostGraduate(postGraduate: PostGraduate)
    suspend fun deletePostGraduate(postGraduate: PostGraduate)

    suspend fun getCategories(): List<Category>
    suspend fun insertCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)

    suspend fun getDirections(): List<ScientificDirection>
    suspend fun insertDirection(direction: ScientificDirection): Long
    suspend fun updateDirection(direction: ScientificDirection)
    suspend fun deleteDirection(direction: ScientificDirection)

    suspend fun insertReward(reward: Reward): Long
    suspend fun updateReward(reward: Reward)
    suspend fun deleteReward(reward: Reward)
}