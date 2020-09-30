package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import git.pbisinski.odloty.BR
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentDashboardBinding
import git.pbisinski.odloty.view.Screen
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.screen.search.SearchScreen
import git.pbisinski.odloty.view.screen.start.SplashScreen
import git.pbisinski.odloty.view.showScreen
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

  override val layoutIdRes: Int = R.layout.fragment_dashboard

  private val vm: DashboardViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = super.onCreateView(inflater, container, savedInstanceState)
    binding.setVariable(BR.viewModel, vm)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.bottomNavigator.attach(fragment = this, screenModels = vm.navigationScreens) { bottomScreenModel ->
      showScreen(screen = bottomScreenModel.screen)
    }
    binding.textviewFirst.text = this.toString()
    if (savedInstanceState == null) {
      showScreen(screen = vm.navigationScreens.first().screen)
    }
  }

  override fun backPressed(): Boolean {
    val entryCount = childFragmentManager.backStackEntryCount
    val canPop = entryCount > 1
    if (canPop) {
      childFragmentManager.popBackStack()
    }
    return canPop
  }

  // TODO: 2020-09-30 make this an interface????? idk
  private fun showScreen(screen: Screen) {
    childFragmentManager.showScreen(
      screen = screen,
      containerResId = binding.navigationContainer.id
    )
  }
}

// TODO: 2020-09-30 move view model to separate file
class DashboardViewModel : ViewModel() {

  val navigationScreens: List<BottomScreenModel> = listOf(
    BottomScreenModel(
      screen = SplashScreen,
      label = "Splash"
    ),
    BottomScreenModel(
      screen = SearchScreen,
      label = "Search"
    )
  )
}

// TODO: 2020-09-30 move model to separate file
class BottomScreenModel(
  val screen: Screen,
  val label: String
)
