package git.pbisinski.odloty.view.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay

abstract class BaseBottomSheetFragment : BaseFragment() {

  companion object {
    private const val ANIMATION_DELAY = 200L
  }

  protected abstract val bottomSheetLayout: View
  protected abstract fun setupView(inflater: LayoutInflater, container: ViewGroup?): View

  private val behavior: BottomSheetBehavior<View> by lazy { BottomSheetBehavior.from(bottomSheetLayout) }
  private var _result: Any? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val rootView = setupView(inflater = inflater, container = container).rootView
    behavior.addBottomSheetCallback(callback)
    viewScope.launchWhenStarted {
      delay(ANIMATION_DELAY)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
    return rootView
  }

  override fun backPressed(): Boolean {
    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    return true
  }

  protected fun dismiss(result: Any? = null) {
    result?.let { _result = it }
    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
  }

  private fun onClose() = _result?.let(navigator::popWithResult) ?: navigator.popScreen()

  private val callback: BottomSheetBehavior.BottomSheetCallback
    get() = object : BottomSheetBehavior.BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
        // NO-OP
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
          BottomSheetBehavior.STATE_COLLAPSED -> onClose()
          else -> Log.d("##BottomSheet", "$newState")
        }
      }
    }
}
