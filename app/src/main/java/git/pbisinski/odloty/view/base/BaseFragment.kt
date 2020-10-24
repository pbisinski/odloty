package git.pbisinski.odloty.view.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import git.pbisinski.odloty.view.Navigator

abstract class BaseFragment : Fragment() {

  protected val viewScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope
  protected val navigator
    get() = activity as? Navigator ?: error("Parent activity does not implement Navigator!")

  protected inline fun <reified T : ViewDataBinding> binding(
    inflater: LayoutInflater,
    @LayoutRes layoutResId: Int,
    container: ViewGroup?
  ): T = DataBindingUtil.inflate(inflater, layoutResId, container, false)

  open fun backPressed(): Boolean = navigator.popScreen()
}
