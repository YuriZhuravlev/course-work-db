package screen.director.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.Cathedra
import data.model.ScientificDirector
import data.repository.cathedra.UseCaseCathedra
import data.repository.director.UseCaseDirector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DirectorEditViewModel(
    private val useCaseDirector: UseCaseDirector,
    private val useCaseCathedra: UseCaseCathedra
) : ViewModel() {
    private val _director = MutableStateFlow<ScientificDirector?>(null)
    val director = _director.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    private val _cathedra = MutableStateFlow(listOf<Cathedra>())
    val cathedra = _cathedra.asStateFlow()

    fun setup(director: Any?) {
        if (director is ScientificDirector) {
            viewModelScope.launch {
                _director.emit(director)
            }
        }
        loadCathedra()
    }

    private fun loadCathedra() {
        viewModelScope.launch {
            _cathedra.emit(useCaseCathedra.getCathedras())
        }
    }

    fun commit(name: String, surname: String, cathedraId: Long) {
        viewModelScope.launch {
            val director = director.value
            if (director == null)
                useCaseDirector.insertDirector(ScientificDirector(EMPTY_ID, name, surname, cathedraId))
            else
                useCaseDirector.updateDirector(director.copy(name = name, surname = surname, cathedraId = cathedraId))
            _result.emit(Resource.success(null))
        }
    }
}
