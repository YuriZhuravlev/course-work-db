package screen.category

import base.ViewModel
import data.Resource
import data.model.Category
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import screen.NavState
import screen.Navigation

class CategoryViewModel(private val useCasePostGraduate: UseCasePostGraduate) : ViewModel() {
    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Empty())
    val categories = _categories.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            _categories.emit(Resource.loading())
            val res = useCasePostGraduate.getCategories()
            _categories.emit(Resource.success(res))
        }
    }

    fun delete(category: Category) {
        viewModelScope.launch {
            useCasePostGraduate.deleteCategory(category)
        }
    }

    fun navigation(state: NavState) {
        viewModelScope.launch {
            Navigation.emitState(state)
        }
    }
}