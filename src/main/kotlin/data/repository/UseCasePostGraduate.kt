package data.repository

import data.model.*

interface UseCasePostGraduate {
    fun getCategories(): List<Category>

    fun getPostGraduatesByCategory(id: Long): List<PostGraduate>
    fun getPostGraduatesByCathedra(id: Long): List<PostGraduate>
    fun getPostGraduatesByDirector(id: Long): List<PostGraduate>

    fun getPostGraduateDetails(id: Long): PostGraduateDetails?
    fun getRewardsByPostGraduate(id: Long): List<Reward>
    fun getDiplomasByPostGraduate(id: Long): List<Diploma>

    fun insertPostGraduate(postGraduate: PostGraduate): Long
    fun updatePostGraduate(postGraduate: PostGraduate)
    fun deletePostGraduate(postGraduate: PostGraduate)

    fun insertCategory(category: Category): Long
    fun updateCategory(category: Category)
    fun deleteCategory(category: Category)

    fun insertDirection(direction: ScientificDirection): Long
    fun updateDirection(direction: ScientificDirection)
    fun deleteDirection(direction: ScientificDirection)

    fun insertReward(reward: Reward): Long
    fun updateReward(reward: Reward)
    fun deleteReward(reward: Reward)
}