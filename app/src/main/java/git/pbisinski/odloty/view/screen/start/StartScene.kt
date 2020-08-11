package git.pbisinski.odloty.view.screen.start

import android.os.Bundle
import git.pbisinski.odloty.view.Scene
import git.pbisinski.odloty.view.Screen

object StartScene : Scene {
  override val activity = StartActivity::class
  override val args: Bundle = Bundle.EMPTY

  sealed class StartScreen : Screen {
    object Splash : StartScreen() {
      override val fragment = SplashFragment::class
      override val args: Bundle = Bundle.EMPTY
    }
  }
}
