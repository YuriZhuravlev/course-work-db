package screen.council.edit

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
private const val NAME = "Название научного совета"

@Composable
fun CouncilEditView(viewModel: CouncilEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val councilsState by viewModel.council.collectAsState()
        val resultState by viewModel.result.collectAsState()

        if (resultState.isSuccess()) {
            viewModel.navigation(NavState.Council)
        }

        val councils = councilsState
        var name by remember { mutableStateOf(councilsState?.name ?: "") }
        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Научный совет")
            if (councils != null) {
                TextField(value = councils.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }
            TextField(value = name, onValueChange = { name = it }, label = { Text(NAME) })
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = { viewModel.commit(name) }) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}