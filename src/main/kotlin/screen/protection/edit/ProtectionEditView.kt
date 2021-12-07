package screen.protection.edit

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
import utils.tryParse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TITLE = "Защита"
private const val ID = "id"
private const val COUNCIL = "Научный совет"
private const val DATE = "Дата"

@Composable
fun ProtectionEditView(viewModel: ProtectionEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val protectionState by viewModel.protection.collectAsState()
        val councils by viewModel.councils.collectAsState()
        val resultState by viewModel.result.collectAsState()

        val protection = protectionState
        var councilId by remember { mutableStateOf(protectionState?.councilId) }
        var date by remember { mutableStateOf(protectionState?.date ?: LocalDate.now()) }
        var dateString by remember { mutableStateOf(date?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "") }
        var expandedList by remember { mutableStateOf(false) }
        val council = councils.find { it.id == councilId }

        if (resultState.isSuccess()) {
            val state = NavState.ProtectionByCouncil
            state.payload = council
            viewModel.navigation(state)
        }
        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = TITLE)
            if (protection != null) {
                TextField(value = protection.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }
            TextField(
                value = dateString,
                onValueChange = { string ->
                    dateString = string
                    date.tryParse(string)?.let { date = it }
                },
                label = { Text(DATE) }
            )
            Row {
                TextField(value = council?.name ?: "Не выбрано", onValueChange = {}, readOnly = true,
                    label = { Text(COUNCIL) })
                Box {
                    Icon(Icons.Default.ArrowDropDown, "", modifier = Modifier.clickable {
                        expandedList = true
                    })
                    DropdownMenu(expanded = expandedList, onDismissRequest = { expandedList = false }) {
                        LazyColumn(Modifier.size(280.dp, 100.dp).padding(8.dp)) {
                            items(councils) { item ->
                                Text(text = item.name, modifier = Modifier.clickable {
                                    councilId = item.id
                                    expandedList = false
                                })
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = (councilId != null),
                onClick = { viewModel.commit(date, councilId!!) }) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}