package git.pbisinski.odloty.view.screen

import android.os.Bundle
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.ActivityMainBinding
import git.pbisinski.odloty.view.base.BaseActivity
import git.pbisinski.odloty.view.screen.dashboard.DashboardScreen

class MainActivity : BaseActivity() {

  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = binding(R.layout.activity_main)
    if (savedInstanceState == null) showScreen(screen = DashboardScreen) // initial screen
  }
}
