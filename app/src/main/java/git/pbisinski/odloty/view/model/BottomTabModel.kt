package git.pbisinski.odloty.view.model

import androidx.annotation.DrawableRes
import git.pbisinski.odloty.view.Screen

data class BottomTabModel(
  val screen: Screen,
  val label: String,
  @DrawableRes val iconResId: Int
)
