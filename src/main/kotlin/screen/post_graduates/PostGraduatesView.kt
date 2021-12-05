package screen.post_graduates

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import base.DBColors
import data.Resource
import data.model.PostGraduate
import screen.NavState
import ui.BigText

@Composable
fun PostGraduatesView(viewModel: PostGraduatesViewModel) {
    val postGraduateState by viewModel.postGraduate.collectAsState()

    val postGraduate = postGraduateState
    when {
        postGraduate is Resource.Empty -> {
            viewModel.category?.let {
                viewModel.loadPostGraduatesByCategory(it)
            }
        }
        postGraduate is Resource.Success -> {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxWidth().height(40.dp).padding(top = 8.dp, start = 20.dp)) {
                    BigText(text = viewModel.category?.name ?: "")
                }
                LazyColumn(Modifier.padding(16.dp)) {
                    itemsIndexed(items = postGraduate.value) { index, item ->
                        ColumnPostGraduate(item, index % 2 != 0) {
                            viewModel.navigation(it)
                        }
                    }
                }
            }
        }
        postGraduate.isLoading() -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun ColumnPostGraduate(
    postGraduate: PostGraduate,
    colored: Boolean,
    onClick: (NavState) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
        .clickable {
            val state = NavState.PostGraduateInfo
            state.payload = postGraduate
            onClick(state)
        }
    if (colored) {
        modifier.background(DBColors().surface)
    }
    Row(modifier = modifier) {
        Text(text = postGraduate.id.toString(), modifier = Modifier.width(50.dp))
        Text(text = postGraduate.name, modifier = Modifier.width(200.dp))
        Text(text = postGraduate.surname, modifier = Modifier.width(200.dp))
    }
}