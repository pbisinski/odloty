package git.pbisinski.odloty.view.screen

import android.os.Bundle
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.ActivityStartBinding
import git.pbisinski.odloty.view.base.BaseActivity
import git.pbisinski.odloty.view.screen.dashboard.DashboardScreen

class StartActivity : BaseActivity() {

  lateinit var binding: ActivityStartBinding

  override val navigationContainer: Int
    get() = binding.rootContainer.id

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = binding(R.layout.activity_start)
    if (savedInstanceState == null) showScreen(screen = DashboardScreen) // initial screen
  }
}
