package screen.diploma.edit

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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import config.EMPTY_ID
import data.model.Cathedra
import data.model.PostGraduate
import data.model.Protection
import data.model.ScientificCouncil
import screen.NavState
import ui.BigText
import ui.BoldText

private const val ID = "ID"
private const val NAME = "Название диплома"
private const val CATHEDRA = "Кафедра"
private const val POST_GRADUATE = "Аспирант"
private const val COUNCIL = "Научный совет"
private const val PROTECTION = "Защита"
private const val COMMIT = "Подтвердить"

@Composable
fun DiplomaEditView(viewModel: DiplomaEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val diplomaState by viewModel.diploma.collectAsState()
        val result by viewModel.result.collectAsState()

        val cathedrasState by viewModel.cathedras.collectAsState()
        val councilsState by viewModel.councils.collectAsState()
        val protections by viewModel.protections.collectAsState()
        val postGraduates by viewModel.postGraduates.collectAsState()

        var name by remember { mutableStateOf(diplomaState?.name ?: "") }
        var cathedra by remember { mutableStateOf<Cathedra?>(null) }
        var council by remember { mutableStateOf<ScientificCouncil?>(null) }
        var protection by remember { mutableStateOf<Protection?>(null) }
        var postGraduate by remember { mutableStateOf<PostGraduate?>(null) }

        if (result.isSuccess()) {
            val state = NavState.ProtectionByCouncil
            state.payload = council
            viewModel.navigation(state)
        }

        val diploma = diplomaState
        // определение защиты на ту, которая была изначально задана, если нет выбора
        if (protection == null && diploma != null) {
            protection = protections.find { it.id == diploma.protectionId }
        }

        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Диплом")

            // ID
            if (diploma != null && diploma.id != EMPTY_ID) {
                TextField(value = diploma.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }

            // Название
            TextField(value = name, onValueChange = { name = it }, label = { Text(NAME) })
            // Выбор кафедры
            SelectableItem(list = cathedrasState, label = CATHEDRA, name = cathedra?.name) {
                Text(it.name, modifier = Modifier.clickable {
                    cathedra = it
                    viewModel.loadPostGraduates(it.id)
                })
            }
            // -- Выбор аспиранта
            if (cathedra != null) {
                SelectableItem(
                    list = postGraduates,
                    label = POST_GRADUATE,
                    name = postGraduate?.let { "${it.name} ${it.surname}" }
                ) {
                    Text("${it.name} ${it.surname}", modifier = Modifier.clickable {
                        postGraduate = it
                    })
                }
            }
            // Выбор Научного совета
            SelectableItem(list = councilsState, label = COUNCIL, name = council?.name) {
                Text(it.name, modifier = Modifier.clickable {
                    council = it
                    viewModel.loadProtections(it.id)
                })
            }
            // -- Выбор защит (из тех, что не имеют диплома)
            SelectableItem(list = protections, label = PROTECTION, name = protection?.let { "${it.id}: ${it.date}" }) {
                Text("${it.id}: ${it.date}", modifier = Modifier.clickable {
                    protection = it
                })
            }
            // Подтвердить
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = (name.isNotBlank() && postGraduate != null && protection != null),
                onClick = { viewModel.commit(name, postGraduate!!.id, protection!!.id) }) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}


@Composable
private fun <T> SelectableItem(list: List<T>, label: String, name: String?, itemView: @Composable (T) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Row {
        TextField(value = name ?: "Не выбрано", onValueChange = {}, readOnly = true,
            label = { Text(label) })
        Box {
            Icon(Icons.Default.ArrowDropDown, "", modifier = Modifier.clickable {
                expanded = true
            })
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, offset = DpOffset(0.dp, 24.dp)) {
                LazyColumn(Modifier.size(280.dp, 100.dp).padding(8.dp)) {
                    items(list) { item ->
                        itemView(item)
                    }
                }
            }
        }
    }
}