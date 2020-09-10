package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import git.pbisinski.odloty.view.Screen

object DashboardScreen : Screen {
  override val fragment = DashboardFragment::class
  override val args: Bundle = Bundle.EMPTY
}
