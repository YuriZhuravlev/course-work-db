package screen.council.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.ScientificCouncil
import data.repository.council.UseCaseCouncil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CouncilEditViewModel(private val useCaseCouncil: UseCaseCouncil) : ViewModel() {
    private val _council = MutableStateFlow<ScientificCouncil?>(null)
    val council = _council.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    fun setup(payload: Any?) {
        if (payload is ScientificCouncil) {
            viewModelScope.launch {
                _council.emit(payload)
            }
        }
    }

    fun commit(name: String) {
        viewModelScope.launch {
            val council = _council.value
            if (council == null)
                useCaseCouncil.insertCouncil(ScientificCouncil(EMPTY_ID, name))
            else
                useCaseCouncil.updateCouncil(council.copy(name = name))
            _result.emit(Resource.success(null))
        }
    }
}