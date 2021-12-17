package screen.direction

import base.ViewModel
import data.Resource
import data.model.ScientificDirection
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DirectionViewModel(private val useCasePostGraduate: UseCasePostGraduate) : ViewModel() {
    private val _direction = MutableStateFlow<Resource<List<ScientificDirection>>>(Resource.Empty())
    val direction = _direction.asStateFlow()

    fun loadDirections() {
        viewModelScope.launch {
            _direction.emit(Resource.loading())
            val res = useCasePostGraduate.getDirections()
            _direction.emit(Resource.success(res))
        }
    }

    fun delete(direction: ScientificDirection) {
        viewModelScope.launch {
            useCasePostGraduate.deleteDirection(direction)
            loadDirections()
        }
    }
}