package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import git.pbisinski.odloty.BR
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentDashboardBinding
import git.pbisinski.odloty.view.Screen
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.screen.search.SearchScreen
import git.pbisinski.odloty.view.screen.start.SplashScreen
import git.pbisinski.odloty.view.showScreen

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

  override val layoutIdRes: Int = R.layout.fragment_dashboard

  private val bottomModel = BottomNavigatorModel(
    screens = listOf(
      BottomNavigatorModel.ScreenModel(screen = SplashScreen, label = "SPLASH"),
      BottomNavigatorModel.ScreenModel(screen = SearchScreen, label = "SEARCH")
    ),
    initialSelectedIndex = 0
  )

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = super.onCreateView(inflater, container, savedInstanceState)
    binding.setVariable(BR.bottomModel, bottomModel)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.bottomNavigator.onScreenChanged = { model: BottomNavigatorModel.ScreenModel ->
      childFragmentManager.showScreen(screen = model.screen, containerResId = binding.navigationContainer.id)
    }
  }

  override fun backPressed(): Boolean {
    val entryCount = childFragmentManager.backStackEntryCount
    val canPop = entryCount > 1
    if (canPop) {
      val tag = childFragmentManager.getBackStackEntryAt(entryCount - 2).name
      childFragmentManager.popBackStack()
      bottomModel.currentlySelectedItem.value = bottomModel.screens.indexOfFirst { model -> model.id == tag }
    }
    return canPop
  }
}

class BottomNavigatorModel(
  val screens: List<ScreenModel>,
  initialSelectedIndex: Int
) {
  val currentlySelectedItem = MutableLiveData(initialSelectedIndex)

  class ScreenModel(
    val screen: Screen,
    val label: String
  ) {
    val id = screen.javaClass.simpleName
  }
}
