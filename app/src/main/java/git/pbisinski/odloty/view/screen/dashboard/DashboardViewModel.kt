package git.pbisinski.odloty.view.screen.dashboard

import androidx.lifecycle.viewModelScope
import git.pbisinski.odloty.R
import git.pbisinski.odloty.view.base.BaseViewModel
import git.pbisinski.odloty.view.model.BottomTabModel
import git.pbisinski.odloty.view.screen.search.SearchScreen
import git.pbisinski.odloty.view.screen.splash.SplashScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

private typealias Change = DashboardState.() -> DashboardState

data class DashboardState(
  val text: String
)

@ExperimentalCoroutinesApi
class DashboardViewModel : BaseViewModel<DashboardIntent, DashboardState, DashboardEvent>() {

  override val initial: DashboardState
    get() = DashboardState(text = "Ekran początkowy")

  val bottomTabs: List<BottomTabModel> = listOf(
    BottomTabModel(
      screen = SplashScreen,
      label = "Pierwszy",
      iconResId = R.drawable.selector_bottom_navigate_search
    ),
    BottomTabModel(
      screen = SearchScreen,
      label = "Drugi",
      iconResId = R.drawable.selector_bottom_navigate_stack
    )
  )

  init {
    viewModelScope.launch(context = Dispatchers.Main) {
      process(DashboardIntent.GoToTab(SearchScreen))
    }
  }

  override fun Flow<DashboardIntent>.toChange(): Flow<Change> = merge(
    filterIsInstance<DashboardIntent.GoToTab>().map { onTabChange(it) }
  )

  override fun Flow<DashboardIntent>.toEvent(): Flow<DashboardEvent> = merge(
    filterIsInstance<DashboardIntent.GoToTab>().map { DashboardEvent.ChangeTab(it.tabScreen) }
  )

  private fun onTabChange(intent: DashboardIntent.GoToTab): Change {
    return { copy(text = "Clicked tab ${intent.tabScreen.name}") }
  }
}
