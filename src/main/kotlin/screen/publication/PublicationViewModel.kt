package screen.publication

import base.ViewModel
import data.Resource
import data.model.ScientificDirector
import data.model.ScientificPublication
import data.repository.publication.UseCasePublication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PublicationViewModel(private val useCasePublication: UseCasePublication) : ViewModel() {
    private val _publications = MutableStateFlow<Resource<List<ScientificPublication>>>(Resource.Empty())
    val publications = _publications.asStateFlow()
    var name = ""
        private set

    fun setup(payload: Any?) {
        viewModelScope.launch {
            if (payload is ScientificDirector) {
                name = "${payload.name} ${payload.surname}"
                _publications.emit(Resource.success(useCasePublication.getPublicationsByDirector(payload.id)))
            }
        }
    }
}