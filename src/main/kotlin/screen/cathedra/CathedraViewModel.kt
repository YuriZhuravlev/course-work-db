package screen.cathedra

import base.ViewModel
import data.Resource
import data.model.Cathedra
import data.repository.cathedra.UseCaseCathedra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CathedraViewModel(private val useCaseCathedra: UseCaseCathedra) : ViewModel() {
    private val _cathedra = MutableStateFlow<Resource<List<Cathedra>>>(Resource.Empty())
    val cathedra = _cathedra.asStateFlow()

    fun loadCathedra() {
        viewModelScope.launch {
            _cathedra.emit(Resource.loading())
            val result = useCaseCathedra.getCathedras()
            _cathedra.emit(Resource.success(result))
        }
    }

    fun delete(cathedra: Cathedra) {
        viewModelScope.launch {
            useCaseCathedra.deleteCathedra(cathedra)
            loadCathedra()
        }
    }
}