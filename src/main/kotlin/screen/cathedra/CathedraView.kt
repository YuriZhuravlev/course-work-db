package screen.cathedra

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
import data.model.Cathedra
import screen.NavState

@Composable
fun CathedraView(viewModel: CathedraViewModel) {
    val cathedraState by viewModel.cathedra.collectAsState()


    val cathedra = cathedraState
    when {
        cathedra is Resource.Empty -> {
            viewModel.loadCathedra()
        }
        cathedra is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().height(40.dp).padding(top = 20.dp, end = 20.dp)) {
                    Icon(imageVector = Icons.Default.Add, modifier = Modifier.clickable {
                        viewModel.navigation(NavState.CathedraEdit)
                    }.align(Alignment.TopEnd), contentDescription = "add")
                }
                LazyColumn(Modifier.padding(16.dp).fillMaxWidth()) {
                    items(items = cathedra.value) { item ->
                        ColumnCathedra(item, onDelete = viewModel::delete) {
                            viewModel.navigation(it)
                        }
                        Divider(modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp))
                    }
                }
            }
        }
        cathedra.isLoading() -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ColumnCathedra(
    cathedra: Cathedra,
    onDelete: (Cathedra) -> Unit,
    onClick: (NavState) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
    Row(modifier = modifier) {
        Text(text = cathedra.id.toString(), modifier = Modifier.width(50.dp).align(Alignment.CenterVertically))
        Text(text = cathedra.name, modifier = Modifier.width(300.dp).align(Alignment.CenterVertically))
        Button(onClick = { onClick(NavState.DirectorByCathedra) }, modifier = Modifier.weight(3f).padding(end = 4.dp)) {
            Text("Научные руководители")
        }
        Button(onClick = { onClick(NavState.PostGraduatesByCathedra) }, modifier = Modifier.weight(2f)) {
            Text("Аспиранты")
        }
        Icon(Icons.Default.Edit, "edit", Modifier.clickable {
            val state = NavState.CategoryEdit
            state.payload = cathedra
            onClick(state)
        }.align(Alignment.CenterVertically))
        Icon(Icons.Default.Delete, "delete", Modifier.clickable {
            onDelete(cathedra)
        }.align(Alignment.CenterVertically))
    }
}
