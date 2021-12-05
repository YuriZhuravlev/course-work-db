package screen.cathedra.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.Cathedra
import data.repository.cathedra.UseCaseCathedra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CathedraEditViewModel(private val useCaseCathedra: UseCaseCathedra) : ViewModel() {
    private val _cathedra = MutableStateFlow<Cathedra?>(null)
    val cathedra = _cathedra.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    fun setup(cathedra: Any?) {
        if (cathedra is Cathedra) {
            viewModelScope.launch {
                _cathedra.emit(cathedra)
            }
        }
    }

    fun commit(name: String) {
        viewModelScope.launch {
            val cathedra = _cathedra.value
            if (cathedra == null)
                useCaseCathedra.insertCathedra(Cathedra(EMPTY_ID, name))
            else
                useCaseCathedra.insertCathedra(cathedra.copy(name = name))
            _result.emit(Resource.success(null))
        }
    }

}