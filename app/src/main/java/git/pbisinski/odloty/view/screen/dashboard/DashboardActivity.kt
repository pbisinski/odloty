package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.ActivityDashboardBinding
import git.pbisinski.odloty.view.base.BaseActivity
import git.pbisinski.odloty.view.screen.dashboard.DashboardScene.DashboardScreen

class DashboardActivity : BaseActivity() {

  private val binding: ActivityDashboardBinding by binding(R.layout.activity_dashboard)

  override val navigationContainer: Int
    get() = binding.rootContainer.id

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    showScreen(screen = DashboardScreen.Search)
  }

}
