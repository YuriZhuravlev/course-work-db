package screen.direction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import data.model.ScientificDirection
import screen.NavState
import ui.BigText

@Composable
fun DirectionView(viewModel: DirectionViewModel) {
    val directionsState by viewModel.direction.collectAsState()


    val directions = directionsState
    when {
        directions is Resource.Empty -> {
            viewModel.loadDirections()
        }
        directions is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().padding(top = 8.dp, start = 20.dp, end = 20.dp)) {
                    BigText(text = "Научные направления")
                    Icon(imageVector = Icons.Default.Add, modifier = Modifier.clickable {
                        viewModel.navigation(NavState.DirectionEdit)
                    }.align(Alignment.TopEnd), contentDescription = "add")
                }
                LazyColumn(Modifier.padding(16.dp)) {
                    items(items = directions.value) { item ->
                        ColumnDirection(item, onDelete = viewModel::delete) {
                            viewModel.navigation(it)
                        }
                        Divider(modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp))
                    }
                }
            }
        }
        directions.isLoading() -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun ColumnDirection(
    direction: ScientificDirection,
    onDelete: (ScientificDirection) -> Unit,
    onClick: (NavState) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
    Row(modifier = modifier) {
        Text(text = direction.id.toString(), modifier = Modifier.width(50.dp))
        Text(text = direction.name, modifier = Modifier.width(400.dp))
        Icon(Icons.Default.Edit, "edit", Modifier.clickable {
            val state = NavState.DirectionEdit
            state.payload = direction
            onClick(state)
        })
        Icon(Icons.Default.Delete, "delete", Modifier.clickable {
            onDelete(direction)
        })
    }
}