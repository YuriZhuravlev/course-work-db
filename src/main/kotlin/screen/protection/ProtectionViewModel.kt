package screen.protection

import base.ViewModel
import data.Resource
import data.model.Protection
import data.model.ProtectionDetails
import data.model.ScientificCouncil
import data.repository.council.UseCaseCouncil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProtectionViewModel(private val useCaseCouncil: UseCaseCouncil) : ViewModel() {
    var council: ScientificCouncil? = null
        private set

    private val _protections = MutableStateFlow<Resource<List<ProtectionDetails>>>(Resource.Empty())
    val protections = _protections.asStateFlow()

    fun setup(payload: Any?) {
        when (payload) {
            is ScientificCouncil -> {
                council = payload
            }
        }
    }

    fun load() {
        viewModelScope.launch {
            _protections.emit(Resource.loading())
            val res = council?.let {
                useCaseCouncil.getProtectionsByCouncil(it)
            }
            _protections.emit(
                if (res != null)
                    Resource.success(res)
                else
                    Resource.failed(Throwable("Council not found!"))
            )
        }
    }

    fun delete(protection: ProtectionDetails) {
        viewModelScope.launch {
            useCaseCouncil.deleteProtection(Protection(protection.id, protection.councilId, protection.date))
            _protections.emit(Resource.loading())
        }
    }
}