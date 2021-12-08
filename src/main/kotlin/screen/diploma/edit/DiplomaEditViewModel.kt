package screen.diploma.edit

import base.ViewModel
import config.EMPTY_ID
import data.Resource
import data.model.*
import data.repository.cathedra.UseCaseCathedra
import data.repository.council.UseCaseCouncil
import data.repository.post_graduate.UseCasePostGraduate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiplomaEditViewModel(
    private val useCaseCouncil: UseCaseCouncil,
    private val useCasePostGraduate: UseCasePostGraduate,
    private val useCaseCathedra: UseCaseCathedra
) : ViewModel() {
    private val _diploma = MutableStateFlow<Diploma?>(null)
    val diploma = _diploma.asStateFlow()

    private val _result = MutableStateFlow<Resource<Any?>>(Resource.Empty())
    val result = _result.asStateFlow()

    private val _cathedras = MutableStateFlow<List<Cathedra>>(listOf())
    val cathedras = _cathedras.asStateFlow()

    private val _postGraduates = MutableStateFlow<List<PostGraduate>>(listOf())
    val postGraduates = _postGraduates.asStateFlow()

    private val _protections = MutableStateFlow<List<Protection>>(listOf())
    val protections = _protections.asStateFlow()

    private val _councils = MutableStateFlow<List<ScientificCouncil>>(listOf())
    val councils = _councils.asStateFlow()

    fun setup(payload: Any?) {
        when (payload) {
            is Diploma -> {
                viewModelScope.launch {
                    _diploma.emit(payload)
                }
            }
            is Protection -> {
                viewModelScope.launch {
                    _diploma.emit(Diploma(EMPTY_ID, "", EMPTY_ID, payload.id))
                    loadProtections(payload.id)
                }
            }
        }
        loadCouncils()
        loadCathedras()
    }

    private fun loadCathedras() {
        viewModelScope.launch {
            _cathedras.emit(useCaseCathedra.getCathedras())
        }
    }

    private fun loadCouncils() {
        viewModelScope.launch {
            _councils.emit(useCaseCouncil.getCouncils())
        }
    }

    fun loadPostGraduates(cathedraId: Long) {
        viewModelScope.launch {
            _postGraduates.emit(useCasePostGraduate.getPostGraduatesByCathedra(cathedraId))
        }
    }

    fun loadProtections(councilId: Long) {
        viewModelScope.launch {
            _protections.emit(useCaseCouncil.getProtectionsByCouncil(councilId).filterDiploms())
        }
    }

    fun commit(name: String, postGraduateId: Long, protectionId: Long) {
        viewModelScope.launch {
            val diploma = _diploma.value
            if (diploma != null && diploma.id != EMPTY_ID) {
                useCaseCouncil.updateDiploma(
                    diploma.copy(
                        name = name,
                        postGraduateId = postGraduateId,
                        protectionId = protectionId
                    )
                )
            } else {
                useCaseCouncil.insertDiploma(
                    Diploma(
                        EMPTY_ID,
                        name = name,
                        postGraduateId = postGraduateId,
                        protectionId = protectionId
                    )
                )
            }
            _result.emit(Resource.success(null))
        }
    }

    private fun List<ProtectionDetails>.filterDiploms(): List<Protection> {
        return this.mapNotNull { item ->
            if (item.diploma == null) {
                Protection(item.id, item.councilId, item.date)
            } else {
                null
            }
        }
    }
}