package screen.director.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screen.NavState
import ui.BigText
import ui.BoldText

private const val ID = "id"
private const val NAME = "Имя"
private const val SURNAME = "Фамилия"
private const val CATHEDRA = "Кафедра"

@Composable
fun DirectorEditView(viewModel: DirectorEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val directorState by viewModel.director.collectAsState()
        val resultState by viewModel.result.collectAsState()
        val cathedras by viewModel.cathedra.collectAsState()

        val director = directorState
        var name by remember { mutableStateOf(directorState?.name ?: "") }
        var surname by remember { mutableStateOf(directorState?.surname ?: "") }
        var cathedraId by remember { mutableStateOf(directorState?.cathedraId) }
        var expandedList by remember { mutableStateOf(false) }
        val cathedra = cathedras.find { it.id == cathedraId }


        if (resultState.isSuccess()) {
            val state = NavState.DirectorByCathedra
            state.payload = cathedra
            viewModel.navigation(state)
        }
        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Научный руководитель")
            if (director != null) {
                TextField(value = director.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }
            TextField(value = name, onValueChange = { name = it }, label = { Text(NAME) })
            TextField(value = surname, onValueChange = { surname = it }, label = { Text(SURNAME) })
            Row {
                TextField(value = cathedra?.name ?: "Не выбрано", onValueChange = {}, readOnly = true,
                    label = { Text(CATHEDRA) })
                Box {
                    Icon(Icons.Default.ArrowDropDown, "", modifier = Modifier.clickable {
                        expandedList = true
                    })
                    DropdownMenu(expanded = expandedList, onDismissRequest = { expandedList = false }) {
                        LazyColumn(Modifier.size(280.dp, 100.dp).padding(8.dp)) {
                            items(cathedras) { item ->
                                Text(text = item.name, modifier = Modifier.clickable {
                                    cathedraId = item.id
                                    expandedList = false
                                })
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = (cathedraId != null),
                onClick = { viewModel.commit(name, surname, cathedraId!!) }) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}