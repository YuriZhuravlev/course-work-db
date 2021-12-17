package screen.category.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import screen.NavState
import ui.BigText
import ui.BoldText

private const val ID = "id"
private const val NAME = "Название категории"

@Composable
fun CategoryEditView(viewModel: CategoryEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val categoryState by viewModel.category.collectAsState()
        val resultState by viewModel.result.collectAsState()

        if (resultState.isSuccess()) {
            viewModel.navigation(NavState.Category)
        }

        val category = categoryState
        var name by remember { mutableStateOf(categoryState?.name ?: "") }
        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Категория аспиранта")
            if (category != null) {
                TextField(value = category.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }
            TextField(value = name, onValueChange = { name = it }, label = { Text(NAME) })
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = { viewModel.commit(name) }) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}