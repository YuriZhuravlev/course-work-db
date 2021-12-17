package screen.publication.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.ScientificPublication
import data.repository.publication.UseCasePublication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class PublicationEditViewModel(private val useCasePublication: UseCasePublication) : ViewModel() {
    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    private val _publication = MutableStateFlow<ScientificPublication?>(null)
    val publication = _publication.asStateFlow()

    fun setup(payload: Any?) {
        viewModelScope.launch {
            if (payload is ScientificPublication) {
                _publication.emit(payload)
            }
        }
    }

    fun commit(name: String, date: LocalDate) {
        viewModelScope.launch {
            val publication = _publication.value!!.copy(name = name, date = date)
            if (publication.id == EMPTY_ID) {
                useCasePublication.insertPublication(publication)
            } else {
                useCasePublication.updatePublication(publication)
            }
            _result.emit(Resource.success(null))
        }
    }
}