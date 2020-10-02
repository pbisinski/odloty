package git.pbisinski.odloty.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import git.pbisinski.odloty.view.Navigator

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

  @get:IdRes
  protected abstract val layoutIdRes: Int

  protected val navigator
    get() = activity as? Navigator ?: error("Parent activity does not implement Navigator!")

  private var _binding: DB? = null
  protected val binding: DB
    get() = _binding ?: error("Trying to access the binding outside of the view lifecycle")

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = DataBindingUtil.inflate(inflater, layoutIdRes, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  open fun backPressed(): Boolean = navigator.popScreen()

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
