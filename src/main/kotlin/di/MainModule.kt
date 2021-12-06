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
import screen.category.CategoryViewModel
import screen.category.edit.CategoryEditViewModel
import screen.cathedra.CathedraViewModel
import screen.cathedra.edit.CathedraEditViewModel
import screen.direction.DirectionViewModel
import screen.direction.edit.DirectionEditViewModel
import screen.director.DirectorViewModel
import screen.director.edit.DirectorEditViewModel
import screen.post_graduates.PostGraduatesViewModel
import screen.post_graduates.details.PostGraduateDetailsViewModel
import screen.post_graduates.edit.PostGraduatesEditViewModel

val MainModule = module {
    single<UseCasePublication> { UseCasePublicationImpl(DAOPostgresql) }
    single<UseCaseDirector> { UseCaseDirectorImpl(DAOPostgresql) }
    single<UseCaseCathedra> { UseCaseCathedraImpl(DAOPostgresql) }
    single<UseCaseCouncil> { UseCaseCouncilImpl(DAOPostgresql) }
    single<UseCasePostGraduate> { UseCasePostGraduateImpl(DAOPostgresql) }

    factory { CategoryViewModel(get()) }
    factory { PostGraduatesViewModel(get()) }
    factory { CategoryEditViewModel(get()) }
    factory { CathedraViewModel(get()) }
    factory { CathedraEditViewModel(get()) }
    factory { DirectorViewModel(get()) }
    factory { DirectorEditViewModel(get(), get()) }
    factory { DirectionViewModel(get()) }
    factory { DirectionEditViewModel(get()) }
    factory { PostGraduatesEditViewModel(get(), get()) }
    factory { PostGraduateDetailsViewModel(get(), get()) }
}