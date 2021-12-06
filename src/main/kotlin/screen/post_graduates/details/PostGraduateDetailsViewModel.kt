package screen.post_graduates.details

import base.ViewModel
import data.Resource
import data.model.*
import data.repository.post_graduate.UseCasePostGraduate
import data.repository.publication.UseCasePublication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostGraduateDetailsViewModel(
    private val useCasePostGraduate: UseCasePostGraduate,
    private val useCasePublication: UseCasePublication
) : ViewModel() {
    private val _postGraduate = MutableStateFlow<Resource<PostGraduateDetails>>(Resource.Empty())
    val postGraduate = _postGraduate.asStateFlow()

    private val _publications = MutableStateFlow<List<ScientificPublication>>(listOf())
    val publications = _publications.asStateFlow()

    private val _rewards = MutableStateFlow<List<Reward>>(listOf())
    val rewards = _rewards.asStateFlow()

    private val _diplomas = MutableStateFlow<List<Diploma>>(listOf())
    val diplomas = _diplomas.asStateFlow()

    private fun load(id: Long) {
        viewModelScope.launch {
            launch {
                _publications.emit(useCasePublication.getPublicationsByPostGraduate(id))
            }
            launch {
                _diplomas.emit(useCasePostGraduate.getDiplomasByPostGraduate(id))
            }
            launch {
                _rewards.emit(useCasePostGraduate.getRewardsByPostGraduate(id))
            }
        }
    }

    fun setup(payload: Any?) {
        viewModelScope.launch {
            if (payload is PostGraduate) {
                _postGraduate.emit(Resource.loading())
                useCasePostGraduate.getPostGraduateDetails(payload.id)?.let {
                    _postGraduate.emit(Resource.success(it))
                    load(payload.id)
                } ?: _postGraduate.emit(Resource.failed(Throwable("Not found by id")))
            } else
                _postGraduate.emit(Resource.failed(Throwable("Failed")))
        }
    }

    fun deleteReward(reward: Reward) {
        viewModelScope.launch {
            useCasePostGraduate.deleteReward(reward)
            val value = _postGraduate.value
            if (value is Resource.Success)
                _rewards.emit(useCasePostGraduate.getRewardsByPostGraduate(value.value.id))
        }
    }
}