package screen.main

import base.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import screen.NavState

class MainViewModel: ViewModel() {
    val state = MutableStateFlow(NavState.Main)
}