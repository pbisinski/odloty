package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import git.pbisinski.odloty.view.Scene
import git.pbisinski.odloty.view.Screen

object DashboardScene : Scene {
  override val activity = DashboardActivity::class
  override val args: Bundle = Bundle.EMPTY

  sealed class DashboardScreen : Screen {
    object Search : DashboardScreen() {
      override val fragment = SearchFragment::class
      override val args: Bundle = Bundle.EMPTY
    }
  }
}
