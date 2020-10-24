package git.pbisinski.odloty.view

import androidx.fragment.app.FragmentManager
import git.pbisinski.odloty.R

fun FragmentManager.showScreen(screen: Screen, unique: Boolean = true) {
  if (unique && fragments.isNotEmpty() && getBackStackEntryAt(backStackEntryCount - 1).name == screen.name) return
  this.beginTransaction().run {
    replace(R.id.navigation_container, screen.fragment.java, screen.args)
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
