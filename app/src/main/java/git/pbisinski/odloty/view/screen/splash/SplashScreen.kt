package git.pbisinski.odloty.view.screen.splash

import android.os.Bundle
import git.pbisinski.odloty.view.Screen

object SplashScreen : Screen {
  override val fragment = SplashFragment::class
  override val args: Bundle = Bundle.EMPTY
  override val name: String = "SplashScreen"
}
