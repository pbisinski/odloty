package git.pbisinski.odloty.view

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

fun FragmentManager.showScreen(screen: Screen, @IdRes containerResId: Int, unique: Boolean = true) {
  if (unique && fragments.isNotEmpty() && getBackStackEntryAt(backStackEntryCount - 1).name == screen.name) return
  this.beginTransaction().run {
    replace(containerResId, screen.fragment.java, screen.args)
    addToBackStack(screen.name)
    commit()
  }
}

fun FragmentManager.canPop(): Boolean = backStackEntryCount > 1

fun FragmentManager.pop(): Boolean {
  val canPop = canPop()
  if (canPop) popBackStack()
  return canPop
}
