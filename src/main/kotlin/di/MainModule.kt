package di

import data.db.DAO
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
import data.repository.reports.UseCaseReports
import data.repository.reports.UseCaseReportsImpl
import org.koin.dsl.module
import screen.category.CategoryViewModel
import screen.category.edit.CategoryEditViewModel
import screen.cathedra.CathedraViewModel
import screen.cathedra.edit.CathedraEditViewModel
import screen.council.CouncilViewModel
import screen.council.edit.CouncilEditViewModel
import screen.diploma.edit.DiplomaEditViewModel
import screen.direction.DirectionViewModel
import screen.direction.edit.DirectionEditViewModel
import screen.director.DirectorViewModel
import screen.director.edit.DirectorEditViewModel
import screen.post_graduates.PostGraduatesViewModel
import screen.post_graduates.details.PostGraduateDetailsViewModel
import screen.post_graduates.edit.PostGraduatesEditViewModel
import screen.protection.ProtectionViewModel
import screen.protection.edit.ProtectionEditViewModel
import screen.publication.PublicationViewModel
import screen.publication.edit.PublicationEditViewModel
import screen.reports.ReportsViewModel
import screen.reward.edit.RewardEditViewModel

val MainModule = module {
    single<DAO> { DAOPostgresql }
    single<UseCasePublication> { UseCasePublicationImpl(get()) }
    single<UseCaseDirector> { UseCaseDirectorImpl(get()) }
    single<UseCaseCathedra> { UseCaseCathedraImpl(get()) }
    single<UseCaseCouncil> { UseCaseCouncilImpl(get()) }
    single<UseCasePostGraduate> { UseCasePostGraduateImpl(get()) }
    single<UseCaseReports> { UseCaseReportsImpl(get()) }

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
    factory { RewardEditViewModel(get()) }
    factory { PublicationEditViewModel(get()) }
    factory { PublicationViewModel(get()) }
    factory { CouncilViewModel(get()) }
    factory { CouncilEditViewModel(get()) }
    factory { ProtectionViewModel(get()) }
    factory { ProtectionEditViewModel(get()) }
    factory { DiplomaEditViewModel(get(), get(), get()) }
    factory { ReportsViewModel(get()) }
}