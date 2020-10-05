package git.pbisinski.odloty.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import git.pbisinski.odloty.view.Navigator

abstract class BaseFragment : Fragment() {

  protected val navigator
    get() = activity as? Navigator ?: error("Parent activity does not implement Navigator!")

  open fun backPressed(): Boolean = navigator.popScreen()

  protected inline fun <reified T : ViewDataBinding> createBindedView(
    @LayoutRes layoutResId: Int,
    inflater: LayoutInflater,
    container: ViewGroup?,
    block: (T.() -> Unit) = {}
  ): View = DataBindingUtil.inflate<T>(inflater, layoutResId, container, false).apply(block).root
}
