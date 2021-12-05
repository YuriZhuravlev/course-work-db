package screen.post_graduates.edit

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
private const val DIRECTOR = "Научный руководитель"
private const val DIRECTION = "Направление"
private const val CATEGORY = "Категория"

@Composable
fun PostGraduatesEditView(viewModel: PostGraduatesEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val postGraduateState by viewModel.postGraduate.collectAsState()
        val resultState by viewModel.result.collectAsState()
        val categories by viewModel.categories.collectAsState()
        val directions by viewModel.directions.collectAsState()
        val directors by viewModel.directors.collectAsState()

        val postGraduate = postGraduateState
        var name by remember { mutableStateOf(postGraduateState?.name ?: "") }
        var surname by remember { mutableStateOf(postGraduateState?.surname ?: "") }
        var categoryId by remember { mutableStateOf(postGraduateState?.categoryId) }
        var directionId by remember { mutableStateOf(postGraduateState?.scientificDirectionId) }
        var directorId by remember { mutableStateOf(postGraduateState?.scientificDirectorId) }
        var expandedCategory by remember { mutableStateOf(false) }
        var expandedDirector by remember { mutableStateOf(false) }
        var expandedDirection by remember { mutableStateOf(false) }
        val category = categories.find { it.id == categoryId }
        val director = directors.find { it.id == directorId }
        val direction = directions.find { it.id == directionId }


        if (resultState.isSuccess()) {
            val state = NavState.Main
//            state.payload = postGraduate
            viewModel.navigation(state)
        }
        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Аспирант")
            if (postGraduate != null) {
                TextField(value = postGraduate.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }
            TextField(value = name, onValueChange = { name = it }, label = { Text(NAME) })
            TextField(value = surname, onValueChange = { surname = it }, label = { Text(SURNAME) })
            Row {
                TextField(value = category?.name ?: "Не выбрано", onValueChange = {}, readOnly = true,
                    label = { Text(CATEGORY) })
                Box {
                    Icon(Icons.Default.ArrowDropDown, "", modifier = Modifier.clickable {
                        expandedCategory = true
                    })
                    DropdownMenu(expanded = expandedCategory, onDismissRequest = { expandedCategory = false }) {
                        LazyColumn(Modifier.size(280.dp, 100.dp).padding(8.dp)) {
                            items(categories) { item ->
                                Text(text = item.name, modifier = Modifier.clickable {
                                    categoryId = item.id
                                    expandedCategory = false
                                })
                            }
                        }
                    }
                }
            }
            Row {
                TextField(value = direction?.name ?: "Не выбрано", onValueChange = {}, readOnly = true,
                    label = { Text(DIRECTION) })
                Box {
                    Icon(Icons.Default.ArrowDropDown, "", modifier = Modifier.clickable {
                        expandedDirection = true
                    })
                    DropdownMenu(expanded = expandedDirection, onDismissRequest = { expandedDirection = false }) {
                        LazyColumn(Modifier.size(280.dp, 100.dp).padding(8.dp)) {
                            items(directions) { item ->
                                Text(text = item.name, modifier = Modifier.clickable {
                                    directionId = item.id
                                    expandedDirection = false
                                })
                            }
                        }
                    }
                }
            }
            Row {
                TextField(value = director?.let { "${it.name} ${it.surname}" } ?: "Не выбрано",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(DIRECTOR) })
                Box {
                    Icon(Icons.Default.ArrowDropDown, "", modifier = Modifier.clickable {
                        expandedDirector = true
                    })
                    DropdownMenu(expanded = expandedDirector, onDismissRequest = { expandedDirector = false }) {
                        LazyColumn(Modifier.size(280.dp, 100.dp).padding(8.dp)) {
                            items(directors) { item ->
                                Text(text = "${item.name} ${item.surname}", modifier = Modifier.clickable {
                                    directorId = item.id
                                    expandedDirector = false
                                })
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = (directorId != null && directionId != null && categoryId != null),
                onClick = { viewModel.commit(name, surname, directorId!!, directionId!!, categoryId!!) }) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}