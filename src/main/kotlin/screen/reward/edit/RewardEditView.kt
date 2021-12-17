package screen.reward.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import config.EMPTY_ID
import screen.NavState
import ui.BigText
import ui.BoldText
import utils.tryParse
import java.time.format.DateTimeFormatter

private const val ID = "id"
private const val NAME = "Название"
private const val DATE = "Дата получения"
private const val POST_GRADUATE = "id аспиранта"

@Composable
fun RewardEditView(viewModel: RewardEditViewModel) {
    Box(Modifier.fillMaxSize()) {
        val rewardState by viewModel.reward.collectAsState()
        val resultState by viewModel.result.collectAsState()

        if (resultState.isSuccess()) {
            viewModel.navigation(NavState.Cathedra)
        }

        val reward = rewardState
        var name by remember { mutableStateOf(rewardState?.name ?: "") }
        var date by remember { mutableStateOf(rewardState?.date) }
        var dateString by remember { mutableStateOf(date?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "") }
        if (date == null) {
            date = reward?.date
        }
        Column(Modifier.align(Alignment.Center)) {
            BigText(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Награда")
            if (reward != null && reward.id != EMPTY_ID) {
                TextField(value = reward.id.toString(), onValueChange = {}, readOnly = true, label = { Text(ID) })
            }
            TextField(value = name, onValueChange = { name = it }, label = { Text(NAME) })
            TextField(
                value = dateString,
                onValueChange = { string ->
                    dateString = string
                    date.tryParse(string)?.let { date = it }
                },
                label = { Text(DATE) }
            )
            TextField(
                value = reward?.postGraduateId.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text(POST_GRADUATE) })
            Text(text = "* задается при создании", modifier = Modifier.padding(start = 40.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { viewModel.commit(name, date!!) },
                enabled = date != null
            ) {
                BoldText(text = "Подтвердить")
            }
        }
    }
}
