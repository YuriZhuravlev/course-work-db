package screen.direction.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.ScientificDirection
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DirectionEditViewModel(private val useCasePostGraduate: UseCasePostGraduate) : ViewModel() {
    private val _direction = MutableStateFlow<ScientificDirection?>(null)
    val direction = _direction.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    fun setup(direction: Any?) {
        if (direction is ScientificDirection) {
            viewModelScope.launch {
                _direction.emit(direction)
            }
        }
    }

    fun commit(name: String) {
        viewModelScope.launch {
            val direction = direction.value
            if (direction == null)
                useCasePostGraduate.insertDirection(ScientificDirection(EMPTY_ID, name))
            else
                useCasePostGraduate.updateDirection(direction.copy(name = name))
            _result.emit(Resource.success(null))
        }
    }

}