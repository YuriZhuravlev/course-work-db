package screen.council

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import data.model.ScientificCouncil
import screen.NavState
import ui.BigText

@Composable
fun CouncilView(viewModel: CouncilViewModel) {
    val councilsState by viewModel.councils.collectAsState()

    when (val councils = councilsState) {
        is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().padding(top = 8.dp, start = 20.dp, end = 20.dp)) {
                    BigText(text = "Научный совет")
                    Icon(imageVector = Icons.Default.Add, modifier = Modifier.clickable {
                        viewModel.navigation(NavState.CouncilEdit)
                    }.align(Alignment.TopEnd), contentDescription = "add")
                }
                LazyColumn(Modifier.padding(16.dp).fillMaxWidth()) {
                    items(items = councils.value) { item ->
                        ColumnCouncil(item, onDelete = viewModel::delete) {
                            viewModel.navigation(it)
                        }
                        Divider(modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp))
                    }
                }
            }
        }
        is Resource.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        else -> {
            viewModel.load()
        }
    }
}

@Composable
private fun ColumnCouncil(
    council: ScientificCouncil,
    onDelete: (ScientificCouncil) -> Unit,
    onClick: (NavState) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
    Row(modifier = modifier) {
        Text(text = council.id.toString(), modifier = Modifier.width(50.dp).align(Alignment.CenterVertically))
        Text(text = council.name, modifier = Modifier.weight(1f).align(Alignment.CenterVertically))
        Button(onClick = {
            val state = NavState.ProtectionByCouncil
            state.payload = council
            onClick(state)
        }) {
            Text("Защиты")
        }
        Icon(Icons.Default.Edit, "edit", Modifier.padding(start = 8.dp).clickable {
            val state = NavState.CategoryEdit
            state.payload = council
            onClick(state)
        }.align(Alignment.CenterVertically))
        Icon(Icons.Default.Delete, "delete", Modifier.padding(end = 16.dp).clickable {
            onDelete(council)
        }.align(Alignment.CenterVertically))
    }
}
