package screen.publication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Resource
import ui.BigText

@Composable
fun PublicationView(viewModel: PublicationViewModel) {
    val publicationsState by viewModel.publications.collectAsState()

    val publications = publicationsState
    when (publications) {
        is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().padding(top = 8.dp, start = 20.dp, bottom = 8.dp)) {
                    BigText(text = "Публикации под руководством ${viewModel.name}")
                }
                LazyColumn() {
                    items(publications.value) { item ->
                        Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                            Text(modifier = Modifier.width(50.dp), text = item.id.toString())
                            Text(modifier = Modifier.weight(1f), text = item.name)
                            Text(modifier = Modifier.width(100.dp), text = item.date.toString())
                        }
                    }
                }
            }
        }
        else -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}