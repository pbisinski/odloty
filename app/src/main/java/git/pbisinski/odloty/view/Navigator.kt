package git.pbisinski.odloty.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

interface Navigator {
  fun showScreen(screen: Screen)
}

interface Screen {
  val fragment: KClass<out Fragment>
  val args: Bundle
  val name: String
}
