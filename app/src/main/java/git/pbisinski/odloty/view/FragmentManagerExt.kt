package git.pbisinski.odloty.view

import androidx.fragment.app.FragmentManager
import git.pbisinski.odloty.R

fun FragmentManager.showScreen(screen: Screen, unique: Boolean = true) {
  if (unique && fragments.isNotEmpty() && getBackStackEntryAt(backStackEntryCount - 1).name == screen.name) return
  this.beginTransaction().run {
    when (screen) {
      is ModalScreen -> add(R.id.navigation_container, screen.fragment.java, screen.args)
      else -> replace(R.id.navigation_container, screen.fragment.java, screen.args)
    }
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

fun FragmentManager.popWithResult(result: Any): Boolean {
  val consumer = fragments.run {
    get(lastIndex - 1) as? ResultConsumer ?: get(lastIndex - 1).childFragmentManager.fragments.last() as? ResultConsumer
  } ?: error("Couldn't pass event to consumer")
  consumer.consume(result)
  return pop()
}
