package screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import screen.NavState

@Composable
fun MainView(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()


    when (state) {
        NavState.Main -> {
            Text("MainState")
        }
        else -> {
            var text by remember { mutableStateOf("Hello, World!") }

            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    text = "Hello, Desktop!"
                }) {
                    Text(text)
                }
            }
        }
    }

}