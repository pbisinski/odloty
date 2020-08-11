package git.pbisinski.odloty.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

interface Navigator {
  fun openScene(scene: Scene)
  fun showScreen(screen: Screen)
}

interface Scene {
  val activity: KClass<out AppCompatActivity>
  val args: Bundle
}

interface Screen {
  val fragment: KClass<out Fragment>
  val args: Bundle
}