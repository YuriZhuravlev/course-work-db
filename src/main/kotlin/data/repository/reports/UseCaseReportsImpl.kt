package data.repository.reports

import data.db.DAO
import data.model.Report

class UseCaseReportsImpl(private val dao: DAO) : UseCaseReports {
    override suspend fun getReportsByCategories(): List<Report> {
        return dao.getReportsByCategories().map {
            Report(it.name, it.count)
        }
    }

    override suspend fun getReportsByDirections(): List<Report> {
        return dao.getReportsByDirections().map {
            Report(it.name, it.count)
        }
    }

    override suspend fun getReportsByCathedras(): List<Report> {
        return dao.getReportsByCathedras().map {
            Report(it.name, it.count)
        }
    }

    override suspend fun getReportsByDirectors(): List<Report> {
        return dao.getReportsByDirectors().map {
            Report(it.name, it.count)
        }
    }
}