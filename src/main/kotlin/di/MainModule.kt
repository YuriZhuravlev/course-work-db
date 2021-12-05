package di

import data.db.DAOPostgresql
import data.repository.cathedra.UseCaseCathedra
import data.repository.cathedra.UseCaseCathedraImpl
import data.repository.council.UseCaseCouncil
import data.repository.council.UseCaseCouncilImpl
import data.repository.director.UseCaseDirector
import data.repository.director.UseCaseDirectorImpl
import data.repository.post_graduate.UseCasePostGraduate
import data.repository.post_graduate.UseCasePostGraduateImpl
import data.repository.publication.UseCasePublication
import data.repository.publication.UseCasePublicationImpl
import org.koin.dsl.module

val MainModule = module {
    single<UseCasePublication> { UseCasePublicationImpl(DAOPostgresql) }
    single<UseCaseDirector> { UseCaseDirectorImpl(DAOPostgresql) }
    single<UseCaseCathedra> { UseCaseCathedraImpl(DAOPostgresql) }
    single<UseCaseCouncil> { UseCaseCouncilImpl(DAOPostgresql) }
    single<UseCasePostGraduate> { UseCasePostGraduateImpl(DAOPostgresql) }
}