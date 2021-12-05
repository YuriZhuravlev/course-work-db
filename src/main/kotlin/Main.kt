import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import base.DBColors
import data.db.DAOPostgresql
import di.MainModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import screen.main.MainView
import screen.main.MainViewModel

@Composable
@Preview
fun App() {
    MaterialTheme(colors = DBColors()) {
        MainView(MainViewModel())
        MainScope().launch(Dispatchers.IO) {
            DAOPostgresql.getCathedras()
        }
    }
}

fun main() = application {
    startKoin {
        printLogger()
        modules(MainModule)
    }

    Window(onCloseRequest = ::exitApplication, title = "Отдел аспирантуры ВУЗа") {
        App()
    }
}
