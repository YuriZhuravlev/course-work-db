package screen.main

import base.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import screen.NavState

class MainViewModel: ViewModel() {
    private val _state = MutableStateFlow(NavState.Main)
    val state = _state.asStateFlow()

    fun emitState(state: NavState) {
        viewModelScope.launch {
            _state.emit(state)
        }
    }
}