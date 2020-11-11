package git.pbisinski.odloty.view.base

import git.pbisinski.odloty.view.Navigator
import git.pbisinski.odloty.view.Screen
import git.pbisinski.odloty.view.pop
import git.pbisinski.odloty.view.popWithResult
import git.pbisinski.odloty.view.showScreen

abstract class BaseNavigationFragment : BaseFragment(), Navigator {

  override fun backPressed(): Boolean = popScreen()

  //region navigation

  override fun showScreen(screen: Screen) = childFragmentManager.showScreen(screen = screen)

  override fun popScreen(): Boolean = childFragmentManager.pop()

  override fun popWithResult(result: Any): Boolean = childFragmentManager.popWithResult(result)

  //endregion
}
