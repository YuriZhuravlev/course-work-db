package screen.post_graduates.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.Category
import data.model.PostGraduate
import data.model.ScientificDirection
import data.model.ScientificDirector
import data.repository.director.UseCaseDirector
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostGraduatesEditViewModel(
    private val useCasePostGraduate: UseCasePostGraduate,
    private val useCaseDirector: UseCaseDirector
) : ViewModel() {
    private val _postGraduate = MutableStateFlow<PostGraduate?>(null)
    val postGraduate = _postGraduate.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    private val _directors = MutableStateFlow(listOf<ScientificDirector>())
    val directors = _directors.asStateFlow()


    private val _directions = MutableStateFlow(listOf<ScientificDirection>())
    val directions = _directions.asStateFlow()

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()


    fun setup(postGraduate: Any?) {
        if (postGraduate is PostGraduate) {
            viewModelScope.launch {
                _postGraduate.emit(postGraduate)
            }
        }
        loadCategories()
        loadDirections()
        loadDirectors()
    }

    private fun loadDirectors() {
        viewModelScope.launch {
            postGraduate.value?.scientificDirectorId?.let { directorId ->
                useCaseDirector.getDirectorDetails(directorId)?.cathedra?.let { cathedra ->
                    _directors.emit(useCaseDirector.getDirectorsByCathedra(cathedra))
                }
            } ?: _directors.emit(useCaseDirector.getDirectors())
        }
    }

    private fun loadDirections() {
        viewModelScope.launch {
            _directions.emit(useCasePostGraduate.getDirections())
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categories.emit(useCasePostGraduate.getCategories())
        }
    }

    fun commit(
        name: String,
        surname: String,
        scientificDirectorId: Long,
        scientificDirectionId: Long,
        categoryId: Long
    ) {
        viewModelScope.launch {
            val postGraduate = postGraduate.value
            if (postGraduate == null)
                useCasePostGraduate.insertPostGraduate(
                    PostGraduate(
                        EMPTY_ID,
                        name,
                        surname,
                        scientificDirectorId,
                        scientificDirectionId,
                        categoryId
                    )
                )
            else
                useCasePostGraduate.updatePostGraduate(
                    postGraduate.copy(
                        name = name, surname = surname,
                        scientificDirectorId = scientificDirectorId,
                        scientificDirectionId = scientificDirectionId, categoryId = categoryId
                    )
                )
            _result.emit(Resource.success(null))
        }
    }
}