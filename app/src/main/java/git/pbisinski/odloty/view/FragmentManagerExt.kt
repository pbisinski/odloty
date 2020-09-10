package git.pbisinski.odloty.view

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

fun FragmentManager.showScreen(screen: Screen, @IdRes containerResId: Int, unique: Boolean = true) {
  val tag = screen.javaClass.simpleName
  if (fragments.isNotEmpty() && getBackStackEntryAt(backStackEntryCount - 1).name == tag && unique) return
  this.beginTransaction().run {
    replace(containerResId, screen.fragment.java, screen.args)
    addToBackStack(tag)
    commit()
  }
}
