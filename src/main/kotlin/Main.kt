import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import base.DBColors
import screen.main.MainView
import screen.main.MainViewModel

@Composable
@Preview
fun App() {
    DesktopMaterialTheme(colors = DBColors()) {
        MainView(MainViewModel())
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Отдел аспирантуры ВУЗа") {
        App()
    }
}
