package screen.category

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
import data.model.Category
import screen.NavState
import ui.BigText

@Composable
fun CategoryView(viewModel: CategoryViewModel) {
    val categoriesState by viewModel.categories.collectAsState()


    val categories = categoriesState
    when {
        categories is Resource.Empty -> {
            viewModel.loadCategories()
        }
        categories is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().padding(top = 8.dp, start = 20.dp, end = 20.dp)) {
                    BigText(text = "Категории аспирантов")
                    Icon(imageVector = Icons.Default.Add, modifier = Modifier.clickable {
                        viewModel.navigation(NavState.CategoryEdit)
                    }.align(Alignment.TopEnd), contentDescription = "add")
                }
                LazyColumn(Modifier.padding(16.dp)) {
                    items(items = categories.value) { item ->
                        ColumnCategory(item, onDelete = viewModel::delete) {
                            viewModel.navigation(it)
                        }
                        Divider(modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp))
                    }
                }
            }
        }
        categories.isLoading() -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun ColumnCategory(
    category: Category,
    onDelete: (Category) -> Unit,
    onClick: (NavState) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
        .clickable {
            val state = NavState.PostGraduatesByCategory
            state.payload = category
            onClick(state)
        }
    Row(modifier = modifier) {
        Text(text = category.id.toString(), modifier = Modifier.width(50.dp))
        Text(text = category.name, modifier = Modifier.weight(1f))
        Icon(Icons.Default.Edit, "edit", Modifier.padding(start = 8.dp).clickable {
            val state = NavState.CategoryEdit
            state.payload = category
            onClick(state)
        })
        Icon(Icons.Default.Delete, "delete", Modifier.padding(end = 16.dp).clickable {
            onDelete(category)
        })
    }
}