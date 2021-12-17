package screen.protection

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
import data.model.ProtectionDetails
import screen.NavState
import ui.BigText

@Composable
fun ProtectionView(viewModel: ProtectionViewModel) {
    val protectionsState by viewModel.protections.collectAsState()

    val protections = protectionsState
    when (protections) {
        is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().padding(top = 8.dp, start = 20.dp, end = 20.dp)) {
                    BigText(text = "Проведенные защиты\nнаучного совета \"${viewModel.council?.name}\"")
                    Icon(imageVector = Icons.Default.Add, modifier = Modifier.clickable {
                        viewModel.navigation(NavState.ProtectionEdit)
                    }.align(Alignment.TopEnd), contentDescription = "add")
                }
                LazyColumn(Modifier.padding(16.dp)) {
                    items(items = protections.value) { item ->
                        ColumnProtection(item, onDelete = viewModel::delete) {
                            viewModel.navigation(it)
                        }
                        Divider(modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp))
                    }
                }
            }
        }
        is Resource.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is Resource.Empty -> {
            viewModel.load()
        }
        is Resource.Failed -> {
            viewModel.navigation(NavState.Council)
        }
    }
}


@Composable
private fun ColumnProtection(
    protection: ProtectionDetails,
    onDelete: (ProtectionDetails) -> Unit,
    onClick: (NavState) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
    Row(modifier = modifier) {
        Text(text = protection.id.toString(), modifier = Modifier.width(50.dp).align(Alignment.CenterVertically))
        Text(text = protection.date.toString(), modifier = Modifier.width(100.dp).align(Alignment.CenterVertically))
        Row(modifier = Modifier.padding(horizontal = 8.dp).weight(1f).clickable {
            val state = NavState.DiplomaEdit
            state.payload = protection.diploma
            onClick(state)
        }) {
            if (protection.diploma == null) {
                Text(text = "Добавить диплом")
                Icon(Icons.Default.Add, "add")
            } else {
                Text("Диплом:")
                Text("\"${protection.diploma.name}\"", Modifier.padding(start = 8.dp))
            }
        }
        Icon(Icons.Default.Edit, "edit", Modifier.padding(start = 8.dp).clickable {
            val state = NavState.ProtectionEdit
            state.payload = protection
            onClick(state)
        }.align(Alignment.CenterVertically))
        Icon(Icons.Default.Delete, "delete", Modifier.padding(end = 16.dp).clickable {
            onDelete(protection)
        }.align(Alignment.CenterVertically))
    }
}