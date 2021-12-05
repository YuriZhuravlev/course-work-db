package screen.director

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Resource
import data.model.ScientificDirector
import screen.NavState
import ui.BigText

@Composable
fun DirectorView(viewModel: DirectorViewModel) {
    val directorsState by viewModel.directors.collectAsState()

    val directors = directorsState
    when {
        directors is Resource.Empty -> {
            viewModel.loadDirector()
        }
        directors is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().padding(top = 8.dp, start = 20.dp, end = 20.dp)) {
                    BigText(text = "Научные руководители\nкафедры \"${viewModel.cathedra?.name}\"")
                    Icon(imageVector = Icons.Default.Add, modifier = Modifier.clickable {
                        viewModel.navigation(NavState.DirectorEdit)
                    }.align(Alignment.TopEnd), contentDescription = "add")
                }
                LazyColumn(Modifier.padding(16.dp)) {
                    items(items = directors.value) { item ->
                        ColumnDirector(item, onDelete = viewModel::delete) {
                            viewModel.navigation(it)
                        }
                    }
                }
            }
        }
        directors.isLoading() -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        directors is Resource.Failed -> {
            viewModel.navigation(NavState.Cathedra)
        }
    }
}

@Composable
private fun ColumnDirector(
    director: ScientificDirector,
    onDelete: (ScientificDirector) -> Unit,
    onClick: (NavState) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
        .clickable {
            val state = NavState.PostGraduatesByDirector
            state.payload = director
            onClick(state)
        }
    Row(modifier = modifier) {
        Text(text = director.id.toString(), modifier = Modifier.width(50.dp))
        Text(text = director.name, modifier = Modifier.width(200.dp))
        Text(text = director.surname, modifier = Modifier.width(200.dp))
        Icon(Icons.Default.Edit, "edit", Modifier.clickable {
            val state = NavState.DirectorEdit
            state.payload = director
            onClick(state)
        })
        Icon(Icons.Default.Delete, "delete", Modifier.clickable {
            onDelete(director)
        })
    }
}