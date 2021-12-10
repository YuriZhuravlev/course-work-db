package screen.post_graduates.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import config.EMPTY_ID
import data.Resource
import data.model.PostGraduate
import data.model.PostGraduateDetails
import data.model.Reward
import data.model.ScientificPublication
import screen.NavState
import ui.BigText
import ui.NormalText
import ui.UnderlineText
import java.time.LocalDate

@Composable
fun PostGraduateDetailsView(viewModel: PostGraduateDetailsViewModel) {
    val postGraduateState by viewModel.postGraduate.collectAsState()

    when (val t = postGraduateState) {
        is Resource.Success -> {
            CardView(Modifier.fillMaxSize().padding(16.dp), t.value, viewModel)
        }
        is Resource.Failed -> {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = "Что-то пошло не так\n(${t.error.message})",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun CardView(
    modifier: Modifier,
    postGraduate: PostGraduateDetails,
    viewModel: PostGraduateDetailsViewModel
) {
    Column(modifier) {
        Row(Modifier.fillMaxWidth()) {
            BigText(Modifier.weight(1f), text = "${postGraduate.name} ${postGraduate.surname}")
            Icon(Icons.Default.Edit, "edit", Modifier.clickable {
                val state = NavState.PostGraduateEdit
                state.payload = PostGraduate(
                    postGraduate.id,
                    postGraduate.name,
                    postGraduate.surname,
                    postGraduate.scientificDirector?.id ?: EMPTY_ID,
                    postGraduate.scientificDirection?.id ?: EMPTY_ID,
                    postGraduate.category?.id ?: EMPTY_ID
                )
                viewModel.navigation(state)
            })
        }
        postGraduate.scientificDirector?.let {
            it.cathedra?.let { cathedra ->
                ItemView("Кафедра:", cathedra.name)
            }
            ItemView("Научный руководитель:", "${it.name} ${it.surname}")
        }
        postGraduate.category?.let {
            ItemView("Категория:", it.name)
        }
        postGraduate.scientificDirection?.let {
            ItemView("Научное направление:", it.name)
        }
        val publications by viewModel.publications.collectAsState()
        ExpandableListView(
            "Публикации",
            publications,
            addView = {
                AddView {
                    val state = NavState.PublicationEdit
                    state.payload = ScientificPublication(EMPTY_ID, "", LocalDate.now(), postGraduate.id)
                    viewModel.navigation(state)
                }
            }
        ) {
            Row {
                Text(modifier = Modifier.width(50.dp), text = it.id.toString())
                Text(modifier = Modifier.width(200.dp), text = it.name)
                Text(modifier = Modifier.width(100.dp), text = it.date.toString())
                Icon(Icons.Default.Edit, "edit", Modifier.clickable {
                    val state = NavState.PublicationEdit
                    state.payload = it
                    viewModel.navigation(state)
                })
                Icon(Icons.Default.Delete, "delete", Modifier.clickable {
                    viewModel.deletePublication(it)
                })
            }
        }

        val diplomas by viewModel.diplomas.collectAsState()
        ExpandableListView("Дипломы", diplomas) {
            Row {
                Text(modifier = Modifier.width(50.dp), text = it.id.toString())
                Text(modifier = Modifier.width(200.dp), text = it.name)
            }
        }

        val rewards by viewModel.rewards.collectAsState()
        ExpandableListView("Награды", rewards,
            addView = {
                AddView {
                    val state = NavState.RewardEdit
                    state.payload = Reward(EMPTY_ID, "", LocalDate.now(), postGraduate.id)
                    viewModel.navigation(state)
                }
            }
        ) {
            Row {
                Text(modifier = Modifier.width(50.dp), text = it.id.toString())
                Text(modifier = Modifier.width(200.dp), text = it.name)
                Text(modifier = Modifier.width(100.dp), text = it.date.toString())
                Icon(Icons.Default.Edit, "edit", Modifier.clickable {
                    val state = NavState.RewardEdit
                    state.payload = it
                    viewModel.navigation(state)
                })
                Icon(Icons.Default.Delete, "delete", Modifier.clickable {
                    viewModel.deleteReward(it)
                })
            }
        }
    }
}

@Composable
fun AddView(onClick: () -> Unit) {
    Row(modifier = Modifier.padding(start = 24.dp).clickable {
        onClick()
    }) {
        Icon(Icons.Default.Add, "Add")
        NormalText(modifier = Modifier.align(Alignment.CenterVertically), text = "Добавить")
    }
}

@Composable
private fun ItemView(text1: String, text2: String) {
    Row {
        NormalText(modifier = Modifier.width(200.dp), text = text1)
        NormalText(modifier = Modifier.width(200.dp), text = text2)
    }
}

@Composable
private fun <T> ExpandableListView(
    label: String,
    list: List<T>,
    addView: @Composable () -> Unit = {},
    itemView: @Composable (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(modifier = Modifier.padding(top = 8.dp)) {
        Icon(
            if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
            "Switch",
            modifier = Modifier.clickable { expanded = !expanded })
        UnderlineText(modifier = Modifier.align(Alignment.CenterVertically), text = label)
    }
    if (expanded) {
        LazyColumn(Modifier.padding(start = 16.dp)) {
            items(list) { item ->
                itemView(item)
            }
        }
        addView()
    }
}