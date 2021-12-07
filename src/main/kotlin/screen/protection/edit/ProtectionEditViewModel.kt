package screen.protection.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.Protection
import data.model.ProtectionDetails
import data.model.ScientificCouncil
import data.repository.council.UseCaseCouncil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProtectionEditViewModel(private val useCaseCouncil: UseCaseCouncil) : ViewModel() {
    private val _protection = MutableStateFlow<Protection?>(null)
    val protection = _protection.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    private val _councils = MutableStateFlow<List<ScientificCouncil>>(listOf())
    val councils = _councils.asStateFlow()

    fun setup(protection: Any?) {
        if (protection is Protection) {
            viewModelScope.launch {
                _protection.emit(protection)
            }
        } else if (protection is ProtectionDetails) {
            viewModelScope.launch {
                _protection.emit(
                    Protection(
                        protection.id,
                        protection.councilId,
                        protection.date
                    )
                )
            }
        }
        loadCouncils()
    }

    private fun loadCouncils() {
        viewModelScope.launch {
            _councils.emit(useCaseCouncil.getCouncils())
        }
    }

    fun commit(date: LocalDate, councilId: Long) {
        viewModelScope.launch {
            val protection = protection.value
            if (protection == null)
                useCaseCouncil.insertProtection(Protection(EMPTY_ID, councilId, date))
            else
                useCaseCouncil.updateProtection(protection.copy(date = date, councilId = councilId))
            _result.emit(Resource.success(null))
        }
    }
}