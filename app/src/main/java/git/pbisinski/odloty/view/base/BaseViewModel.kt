package git.pbisinski.odloty.view.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel<INTENT : Any, STATE : Any, EVENT : Any> : ViewModel() {

  protected abstract val initial: STATE
  protected abstract fun Flow<INTENT>.toChange(): Flow<STATE.() -> STATE>
  protected abstract fun Flow<INTENT>.toEvent(): Flow<EVENT>

  @ExperimentalCoroutinesApi
  private val _intentFlow = MutableSharedFlow<INTENT>()

  protected val devTag: String
    get() = "##${this.javaClass.simpleName}"

  @ExperimentalCoroutinesApi
  val state: Flow<STATE> by lazy {
    _intentFlow
      .shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed()
      )
      .toChange()
      .scan(initial) { currentState, process -> currentState.process() }
      .catch { Log.e(devTag, "Flow StateChange error!\n$it") }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = initial
      )
  }

  @ExperimentalCoroutinesApi
  val event: Flow<EVENT> by lazy {
    _intentFlow
      .shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed()
      )
      .toEvent()
      .catch { Log.e(devTag, "Flow Event error!\n$it") }
  }

  @ExperimentalCoroutinesApi
  suspend fun process(intent: INTENT) {
    _intentFlow.emit(intent)
  }

  protected fun <T : Any> suspendedFlow(block: suspend () -> T): Flow<T> = flow { emit(block()) }
}
