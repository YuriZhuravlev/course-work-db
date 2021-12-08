package screen.diploma.edit

import androidx.compose.runtime.*
import data.model.PostGraduate
import data.model.Protection
import data.model.ScientificCouncil
import screen.NavState

@Composable
fun DiplomaEditView(viewModel: DiplomaEditViewModel) {
    val diplomaState by viewModel.diploma.collectAsState()
    val result by viewModel.result.collectAsState()
    val councilsState by viewModel.councils.collectAsState()
    val protections by viewModel.protections.collectAsState()
    val postGraduates by viewModel.postGraduates.collectAsState()
    var name by remember { mutableStateOf(diplomaState?.name ?: "") }
    var council by remember { mutableStateOf<ScientificCouncil?>(null) }
    var protection by remember { mutableStateOf<Protection?>(null) }
    var postGraduate by remember { mutableStateOf<PostGraduate?>(null) }

    // определение защиты на ту, которая была изначально задана, если нет выбора
    if (protection == null && diplomaState != null) {
        protection = protections.find { it.id == diplomaState?.protectionId }
    }

    // ID
    // Название
    // Выбор кафедры
    // -- Выбор аспиранта
    // Выбор Научного совета
    // -- Выбор защит (из тех, что не имеют диплома)
    // Подтвердить

    if (result.isSuccess()) {
        val state = NavState.ProtectionByCouncil
        state.payload = council
        viewModel.navigation(state)
    }
}