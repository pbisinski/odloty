package git.pbisinski.odloty.view.screen.search

import android.os.Bundle
import git.pbisinski.odloty.view.Screen

object SearchScreen : Screen {
  override val fragment = SearchFragment::class
  override val args: Bundle = Bundle.EMPTY
}
