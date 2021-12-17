package screen.reward.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.Reward
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class RewardEditViewModel(private val useCasePostGraduate: UseCasePostGraduate) : ViewModel() {
    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    private val _reward = MutableStateFlow<Reward?>(null)
    val reward = _reward.asStateFlow()

    fun setup(payload: Any?) {
        viewModelScope.launch {
            if (payload is Reward) {
                _reward.emit(payload)
            }
        }
    }

    fun commit(name: String, date: LocalDate) {
        viewModelScope.launch {
            val reward = _reward.value!!.copy(name = name, date = date)
            if (reward.id == EMPTY_ID) {
                useCasePostGraduate.insertReward(reward)
            } else {
                useCasePostGraduate.updateReward(reward)
            }
            _result.emit(Resource.success(null))
        }
    }
}