package screen.reports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Resource
import ui.BigText
import ui.NormalText

private const val TITLE = "Количество дипломов "
private const val EMPTY = "Выберите параметр для получения отчета"
private val PARAMS = arrayOf("по категориям", "по научным направлениям", "по кафедрам", "по научным руководителям")

@Composable
fun ReportsView(viewModel: ReportsViewModel) {
    val reportsState by viewModel.reports.collectAsState()
    var state by remember { mutableStateOf(-1) }
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxWidth().padding(top = 8.dp, start = 20.dp, end = 20.dp)) {
            BigText(
                modifier = Modifier.padding(bottom = 8.dp),
                text = if (state == -1) EMPTY else TITLE + PARAMS[state]
            )
        }

        Column(Modifier.selectableGroup().padding(start = 16.dp)) {
            Row(Modifier.padding(4.dp)) {
                RadioButton(selected = state == 0, onClick = {
                    viewModel.loadByCategories()
                    state = 0
                }, modifier = Modifier.size(14.dp))
                NormalText(Modifier.align(Alignment.CenterVertically).padding(start = 16.dp), text = PARAMS[0])
            }
            Row(Modifier.padding(4.dp)) {
                RadioButton(selected = state == 1, onClick = {
                    viewModel.loadByDirections()
                    state = 1
                }, modifier = Modifier.size(14.dp))
                NormalText(Modifier.align(Alignment.CenterVertically).padding(start = 16.dp), text = PARAMS[1])
            }
            Row(Modifier.padding(4.dp)) {
                RadioButton(selected = state == 2, onClick = {
                    viewModel.loadByCathedras()
                    state = 2
                }, modifier = Modifier.size(14.dp))
                NormalText(Modifier.align(Alignment.CenterVertically).padding(start = 16.dp), text = PARAMS[2])
            }
            Row(Modifier.padding(4.dp)) {
                RadioButton(selected = state == 3, onClick = {
                    viewModel.loadByDirectors()
                    state = 3
                }, modifier = Modifier.size(14.dp))
                NormalText(Modifier.align(Alignment.CenterVertically).padding(start = 16.dp), text = PARAMS[3])
            }
        }
        when (val reports = reportsState) {
            is Resource.Success -> {
                LazyColumn(Modifier.padding(16.dp).fillMaxWidth()) {
                    items(reports.value) { item ->
                        Row {
                            Text(item.name, Modifier.width(300.dp))
                            Text(item.count.toString())
                        }
                        Divider(modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp))
                    }
                }
            }
            is Resource.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            else -> {

            }
        }
    }
}