import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import base.DBColors
import di.MainModule
import org.koin.core.context.startKoin
import screen.main.MainView
import screen.main.MainViewModel

@Composable
@Preview
fun App() {
    MaterialTheme(colors = DBColors()) {
        MainView(MainViewModel())
    }
}

fun main() = application {
    startKoin {
        printLogger()
        modules(MainModule)
    }

    Window(onCloseRequest = ::exitApplication, title = "Отдел аспирантуры ВУЗа") {
        window.setSize(1024, 768)
        App()
    }
}
