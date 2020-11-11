package git.pbisinski.odloty.view.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import git.pbisinski.odloty.view.Navigator
import git.pbisinski.odloty.view.Screen
import git.pbisinski.odloty.view.pop
import git.pbisinski.odloty.view.showScreen

abstract class BaseActivity : AppCompatActivity(), Navigator {

  protected inline fun <reified T : ViewDataBinding> binding(@LayoutRes resId: Int): T =
    DataBindingUtil.setContentView(this, resId)

  override fun onBackPressed() {
    val currentFragment = supportFragmentManager.fragments.lastOrNull() as? BaseFragment
    val backHandled = currentFragment?.backPressed() ?: false
    if (!backHandled && !popScreen()) finish()
  }

  //region navigation

  override fun showScreen(screen: Screen) = supportFragmentManager.showScreen(screen = screen)

  override fun popScreen(): Boolean = supportFragmentManager.pop()

  //endregion
}
