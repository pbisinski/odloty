package git.pbisinski.odloty.view.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DisposableVar<T : Any> : ReadWriteProperty<Fragment, T>, LifecycleObserver {

  private var _holder: T? = null

  override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
    _holder ?: error("Trying to access disposed property!")

  override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
    thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
    _holder = value
    thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun dispose() {
    _holder = null
  }
}
