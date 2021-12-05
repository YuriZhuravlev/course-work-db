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
import screen.post_graduates.PostGraduatesView
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
                    PostGraduatesView(get().get())
                }
                NavState.Cathedra -> {}
                NavState.Council -> {}
                NavState.Protection -> {}
                else -> {
                    Text(state.name)
                }
            }
        }
    }
}