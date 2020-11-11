package git.pbisinski.odloty.view.screen.search

import android.os.Bundle
import git.pbisinski.odloty.view.Screen

object SearchScreen : Screen {
  override val fragment = SearchFragment::class
  override val args: Bundle = Bundle.EMPTY
  override val name: String = "SearchScreen"
}

sealed class SearchIntent {
  class Download(val query: String) : SearchIntent()

  sealed class Route : SearchIntent() {
    object GoToSplash : Route()
  }
}

sealed class SearchEvent {
  class RouteTo(
    val destination: Screen
  ) : SearchEvent()
}
