package git.pbisinski.odloty.view.screen.start

import android.os.Bundle
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.ActivityStartBinding
import git.pbisinski.odloty.view.base.BaseActivity
import git.pbisinski.odloty.view.screen.start.StartScene.StartScreen

class StartActivity : BaseActivity() {

  private val binding: ActivityStartBinding by binding(R.layout.activity_start)

  override val navigationContainer: Int
    get() = binding.rootContainer.id

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    showScreen(screen = StartScreen.Splash)
  }
}
