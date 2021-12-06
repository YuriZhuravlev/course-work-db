package screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import base.DBColors
import org.koin.core.context.GlobalContext.get
import screen.NavState
import screen.category.CategoryView
import screen.category.edit.CategoryEditView
import screen.category.edit.CategoryEditViewModel
import screen.cathedra.CathedraView
import screen.cathedra.edit.CathedraEditView
import screen.direction.DirectionView
import screen.direction.edit.DirectionEditView
import screen.direction.edit.DirectionEditViewModel
import screen.director.DirectorView
import screen.director.DirectorViewModel
import screen.director.edit.DirectorEditView
import screen.director.edit.DirectorEditViewModel
import screen.post_graduates.PostGraduatesView
import screen.post_graduates.PostGraduatesViewModel
import screen.post_graduates.details.PostGraduateDetailsView
import screen.post_graduates.details.PostGraduateDetailsViewModel
import screen.post_graduates.edit.PostGraduatesEditView
import screen.post_graduates.edit.PostGraduatesEditViewModel
import ui.MenuView

@Composable
fun MainView(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()

    Row {
        MenuView(Modifier.fillMaxHeight().width(200.dp)) {
            viewModel.emitState(it)
        }
        Box(Modifier.fillMaxHeight().width(2.dp).background(DBColors().primary).padding(horizontal = 8.dp))
        Box(Modifier.fillMaxSize()) {
            when (state) {
                NavState.Main -> {}
                NavState.Category -> {
                    CategoryView(get().get())
                }
                NavState.PostGraduatesByCategory -> {
                    PostGraduatesView(get().get<PostGraduatesViewModel>().apply { setup(state.payload) })
                }
                NavState.CategoryEdit -> {
                    CategoryEditView(get().get<CategoryEditViewModel>().apply { setup(state.payload) })
                }
                NavState.Cathedra -> {
                    CathedraView(get().get())
                }
                NavState.CathedraEdit -> {
                    CathedraEditView(get().get())
                }
                NavState.DirectorByCathedra -> {
                    DirectorView(get().get<DirectorViewModel>().apply { setup(state.payload) })
                }
                NavState.DirectorEdit -> {
                    DirectorEditView(get().get<DirectorEditViewModel>().apply { setup(state.payload) })
                }
                NavState.PostGraduatesByDirector -> {
                    PostGraduatesView(get().get<PostGraduatesViewModel>().apply { setup(state.payload) })
                }
                NavState.PostGraduatesByCathedra -> {
                    PostGraduatesView(get().get<PostGraduatesViewModel>().apply { setup(state.payload) })
                }
                NavState.Direction -> {
                    DirectionView(get().get())
                }
                NavState.DirectionEdit -> {
                    DirectionEditView(get().get<DirectionEditViewModel>().apply { setup(state.payload) })
                }
                NavState.PostGraduateEdit -> {
                    PostGraduatesEditView(get().get<PostGraduatesEditViewModel>().apply { setup(state.payload) })
                }
                NavState.PostGraduateDetails -> {
                    PostGraduateDetailsView(get().get<PostGraduateDetailsViewModel>().apply { setup(state.payload) })
                }
//                NavState.Council -> {}
//                NavState.ProtectionByCouncil -> {}
                else -> {
                    Text(state.name)
                }
            }
        }
    }
}