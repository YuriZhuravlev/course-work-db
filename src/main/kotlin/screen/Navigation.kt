package screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object Navigation {
    private val _state = MutableStateFlow(NavState.Main)
    val state = _state.asStateFlow()

    suspend fun emitState(state: NavState) {
        _state.emit(state)
    }
}