package screen.diploma.edit

import base.ViewModel
import data.model.Diploma
import data.repository.cathedra.UseCaseCathedra
import data.repository.council.UseCaseCouncil
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiplomaEditViewModel(
    private val useCaseCouncil: UseCaseCouncil,
    private val useCasePostGraduate: UseCasePostGraduate,
    private val useCaseCathedra: UseCaseCathedra
) : ViewModel() {
    private val _diploma = MutableStateFlow<Diploma?>(null)
    val diploma = _diploma.asStateFlow()

    fun setup(payload: Any?) {
        if (payload is Diploma) {
            viewModelScope.launch {
                _diploma.emit(payload)
            }
        }
        loadCouncils()
        loadCathedras()
    }

    private fun loadCathedras() {
        TODO("Not yet implemented")
    }

    private fun loadCouncils() {
        TODO("Not yet implemented")
    }
}