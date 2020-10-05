package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import git.pbisinski.odloty.BR
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentDashboardBinding
import git.pbisinski.odloty.view.Navigator
import git.pbisinski.odloty.view.Screen
import git.pbisinski.odloty.view.pop
import git.pbisinski.odloty.view.showScreen
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.screen.search.SearchScreen
import git.pbisinski.odloty.view.screen.start.SplashScreen
import git.pbisinski.odloty.view.utils.DisposableVar
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment(), Navigator {

  private val vm: DashboardViewModel by viewModel()
  private var binding: FragmentDashboardBinding by DisposableVar()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    createBindedView<FragmentDashboardBinding>(
      layoutResId = R.layout.fragment_dashboard,
      inflater = inflater,
      container = container,
      block = {
        setVariable(BR.viewModel, vm)
        binding = this
      }
    )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.textviewFirst.text = this.toString()
    binding.bottomNavigator.attach(
      fragment = this,
      screenModels = vm.navigationScreens,
      onNavigate = ::showScreen
    )
    if (childFragmentManager.backStackEntryCount == 0) showScreen(screen = vm.initialScreen)
  }

  override fun backPressed(): Boolean = popScreen()

  override fun showScreen(screen: Screen) {
    childFragmentManager.showScreen(screen = screen, containerResId = binding.navigationContainer.id)
  }

  override fun popScreen(): Boolean = childFragmentManager.pop()
}

// TODO: 2020-09-30 move view model to separate file
class DashboardViewModel : ViewModel() {

  val navigationScreens: List<BottomScreenModel> = listOf(
    BottomScreenModel(
      screen = SplashScreen,
      label = "Pierwszy"
    ),
    BottomScreenModel(
      screen = SearchScreen,
      label = "Drugi"
    )
  )

  val initialScreen: Screen = navigationScreens.first().screen
}

// TODO: 2020-09-30 move model to separate file
class BottomScreenModel(
  val screen: Screen,
  val label: String
)
