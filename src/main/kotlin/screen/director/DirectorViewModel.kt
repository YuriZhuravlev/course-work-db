package screen.director

import base.ViewModel
import data.Resource
import data.model.Cathedra
import data.model.ScientificDirector
import data.repository.director.UseCaseDirector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DirectorViewModel(private val useCaseDirector: UseCaseDirector) : ViewModel() {
    var cathedra: Cathedra? = null
        private set

    private val _directors = MutableStateFlow<Resource<List<ScientificDirector>>>(Resource.Empty())
    val directors = _directors.asStateFlow()

    fun setup(payload: Any?) {
        when (payload) {
            is Cathedra -> {
                cathedra = payload
            }
        }
    }

    fun loadDirector() {
        viewModelScope.launch {
            _directors.emit(Resource.loading())
            val res = cathedra?.let {
                useCaseDirector.getDirectorsByCathedra(it)
            }
            _directors.emit(
                if (res != null)
                    Resource.success(res)
                else
                    Resource.failed(Throwable("Cathedra not found!"))
            )
        }
    }

    fun delete(director: ScientificDirector) {
        viewModelScope.launch {
            useCaseDirector.deleteDirector(director)
            _directors.emit(Resource.loading())
        }
    }
}