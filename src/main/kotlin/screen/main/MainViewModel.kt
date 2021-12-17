package screen.main

import base.ViewModel
import kotlinx.coroutines.launch
import screen.NavState
import screen.Navigation

class MainViewModel : ViewModel() {
    val state get() = Navigation.state

    fun emitState(state: NavState) {
        viewModelScope.launch {
            Navigation.emitState(state)
        }
    }
}