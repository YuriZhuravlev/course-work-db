package screen.reports

import base.ViewModel
import data.Resource
import data.model.Report
import data.repository.reports.UseCaseReports
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReportsViewModel(private val useCaseReports: UseCaseReports) : ViewModel() {
    private val _reports = MutableStateFlow<Resource<List<Report>>>(Resource.Empty())
    val reports = _reports.asStateFlow()

    fun loadByCathedras() {
        viewModelScope.launch {
            _reports.emit(Resource.loading())
            _reports.emit(Resource.success(useCaseReports.getReportsByCathedras()))
        }
    }

    fun loadByCategories() {
        viewModelScope.launch {
            _reports.emit(Resource.loading())
            _reports.emit(Resource.success(useCaseReports.getReportsByCategories()))
        }
    }

    fun loadByDirectors() {
        viewModelScope.launch {
            _reports.emit(Resource.loading())
            _reports.emit(Resource.success(useCaseReports.getReportsByDirectors()))
        }
    }

    fun loadByDirections() {
        viewModelScope.launch {
            _reports.emit(Resource.loading())
            _reports.emit(Resource.success(useCaseReports.getReportsByDirections()))
        }
    }
}