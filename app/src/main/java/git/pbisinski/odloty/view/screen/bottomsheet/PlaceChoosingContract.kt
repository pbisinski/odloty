package git.pbisinski.odloty.view.screen.bottomsheet

import android.os.Bundle
import git.pbisinski.odloty.view.ModalScreen

object PlaceChoosingScreen : ModalScreen {
  override val fragment = PlaceChoosingFragment::class
  override val args: Bundle = Bundle.EMPTY
  override val name: String = "PlaceChoosing"
}
