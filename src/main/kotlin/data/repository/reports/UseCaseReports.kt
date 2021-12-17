package data.repository.reports

import data.model.Report

interface UseCaseReports {
    suspend fun getReportsByCategories(): List<Report>
    suspend fun getReportsByDirections(): List<Report>
    suspend fun getReportsByCathedras(): List<Report>
    suspend fun getReportsByDirectors(): List<Report>
}