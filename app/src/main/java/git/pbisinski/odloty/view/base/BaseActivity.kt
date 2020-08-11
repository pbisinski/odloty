package git.pbisinski.odloty.view.base

import android.content.Intent
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import git.pbisinski.odloty.view.Navigator
import git.pbisinski.odloty.view.Scene
import git.pbisinski.odloty.view.Screen

abstract class BaseActivity : AppCompatActivity(), Navigator {

  @get:IdRes
  protected abstract val navigationContainer: Int

  protected inline fun <reified T : ViewDataBinding> binding(@LayoutRes resId: Int): Lazy<T> =
    lazy { DataBindingUtil.setContentView<T>(this, resId) }

  override fun onBackPressed() {
    val currentFragment = supportFragmentManager.fragments.lastOrNull() as? BaseFragment<*> ?: error("")
    if (!currentFragment.backPressed()) {
      finish()
    }
  }

  override fun openScene(scene: Scene) {
    Intent(this, scene.activity.java).let(::startActivity)
  }

  override fun showScreen(screen: Screen) {
    supportFragmentManager.beginTransaction().run {
      val tag = screen.fragment.simpleName
      replace(navigationContainer, screen.fragment.java, screen.args)
      addToBackStack(tag)
      commit()
    }
  }
}
