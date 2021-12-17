package screen.council

import base.ViewModel
import data.Resource
import data.model.ScientificCouncil
import data.repository.council.UseCaseCouncil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CouncilViewModel(private val useCaseCouncil: UseCaseCouncil) : ViewModel() {
    private val _councils = MutableStateFlow<Resource<List<ScientificCouncil>>>(Resource.Empty())
    val councils = _councils.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _councils.emit(Resource.loading())
            _councils.emit(Resource.success(useCaseCouncil.getCouncils()))
        }
    }

    fun delete(council: ScientificCouncil) {
        viewModelScope.launch {
            useCaseCouncil.deleteCouncil(council)
            _councils.emit(Resource.success(useCaseCouncil.getCouncils()))
        }
    }
}