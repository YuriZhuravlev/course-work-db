package screen.post_graduates

import base.ViewModel
import data.Resource
import data.model.Category
import data.model.PostGraduate
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostGraduatesViewModel(private val useCasePostGraduate: UseCasePostGraduate) : ViewModel() {
    var category: Category? = null
        private set

    private val _postGraduates = MutableStateFlow<Resource<List<PostGraduate>>>(Resource.Empty())
    val postGraduate = _postGraduates.asStateFlow()

    fun setup(payload: Any?) {
        when (payload) {
            is Category -> {
                category = payload
            }
        }
    }

    fun loadPostGraduatesByCategory(category: Category) {
        this.category = category
        viewModelScope.launch {
            val result = useCasePostGraduate.getPostGraduatesByCategory(category.id)
            _postGraduates.emit(Resource.success(result))
        }
    }
}