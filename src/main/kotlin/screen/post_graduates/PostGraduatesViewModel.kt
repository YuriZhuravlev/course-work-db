package screen.post_graduates

import base.ViewModel
import data.Resource
import data.model.Category
import data.model.Cathedra
import data.model.PostGraduate
import data.model.ScientificDirector
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostGraduatesViewModel(private val useCasePostGraduate: UseCasePostGraduate) : ViewModel() {
    var category: Category? = null
        private set
    var cathedra: Cathedra? = null
        private set
    var director: ScientificDirector? = null
        private set

    private val _postGraduates = MutableStateFlow<Resource<List<PostGraduate>>>(Resource.Empty())
    val postGraduate = _postGraduates.asStateFlow()

    fun setup(payload: Any?) {
        when (payload) {
            is Category -> {
                category = payload
            }
            is Cathedra -> {
                cathedra = payload
            }
            is ScientificDirector -> {
                director = payload
            }
        }
    }

    fun loadPostGraduates() {
        viewModelScope.launch {
            try {
                val result = when {
                    (category != null) -> {
                        useCasePostGraduate.getPostGraduatesByCategory(category!!.id)
                    }
                    (cathedra != null) -> {
                        useCasePostGraduate.getPostGraduatesByCathedra(cathedra!!.id)
                    }
                    (director != null) -> {
                        useCasePostGraduate.getPostGraduatesByDirector(director!!.id)
                    }
                    else -> {
                        null
                    }
                }
                _postGraduates.emit(if (result != null) Resource.success(result) else Resource.Failed(Throwable("Error!")))
            } catch (e: Exception) {
                e.printStackTrace()
                _postGraduates.emit(Resource.failed(e))
            }
        }
    }

    fun getHeader(): String {
        return when {
            (category != null) -> {
                "Список аспирантов по категории \"${category?.name}\""
            }
            (cathedra != null) -> {
                "Список аспирантов по кафедре \"${cathedra?.name}\""
            }
            (director != null) -> {
                "Список аспирантов по научному руководителю ${director?.name} ${director?.surname}"
            }
            else -> {
                "Unknown"
            }
        }
    }
}