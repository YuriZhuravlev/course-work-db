package screen.cathedra.edit

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
private const val NAME = "Название кафедры"

@Composable
fun CathedraEditView(viewModel: CathedraEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val cathedraState by viewModel.cathedra.collectAsState()
        val resultState by viewModel.result.collectAsState()

        if (resultState.isSuccess()) {
            viewModel.navigation(NavState.Cathedra)
        }

        val cathedra = cathedraState
        var name by remember { mutableStateOf(cathedraState?.name ?: "") }
        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Кафедра")
            if (cathedra != null) {
                TextField(value = cathedra.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }
            TextField(value = name, onValueChange = { name = it }, label = { Text(NAME) })
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = { viewModel.commit(name) }) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}