package screen.category.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.Category
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryEditViewModel(private val useCasePostGraduate: UseCasePostGraduate) : ViewModel() {
    private val _category = MutableStateFlow<Category?>(null)
    val category = _category.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    fun setup(category: Any?) {
        if (category is Category) {
            viewModelScope.launch {
                _category.emit(category)
            }
        }
    }

    fun commit(name: String) {
        viewModelScope.launch {
            val category = category.value
            if (category == null)
                useCasePostGraduate.insertCategory(Category(EMPTY_ID, name))
            else
                useCasePostGraduate.updateCategory(category.copy(name = name))
            _result.emit(Resource.success(null))
        }
    }

}