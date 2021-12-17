package base

import kotlinx.coroutines.*
import screen.NavState
import screen.Navigation
import kotlin.coroutines.cancellation.CancellationException

open class ViewModel() {
    protected val viewModelScope = MainScope() + CoroutineName("$javaClass") + Dispatchers.IO
    fun close() {
        viewModelScope.cancel(EndViewModel())
    }

    fun navigation(state: NavState) {
        viewModelScope.launch {
            Navigation.emitState(state)
        }
    }

    inner class EndViewModel() : CancellationException()
}