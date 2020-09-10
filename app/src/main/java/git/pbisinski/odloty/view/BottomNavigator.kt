package git.pbisinski.odloty.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import git.pbisinski.odloty.R
import git.pbisinski.odloty.view.screen.dashboard.BottomNavigatorModel
import git.pbisinski.odloty.view.utils.getChildViews

class BottomNavigator @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  companion object {
    private val BUTTON_PARAMS = LayoutParams(0, LayoutParams.MATCH_PARENT, 1F)
    private const val INVALID_ID = -1

    @BindingAdapter("currentButton")
    @JvmStatic
    fun BottomNavigator.setSelected(buttonId: Int) {
      if (buttonId != currentlySelected) {
        currentlySelected = buttonId
      }
    }

    @InverseBindingAdapter(attribute = "currentlySelected", event = "currentlySelectedAttrChanged")
    @JvmStatic
    fun BottomNavigator.getCurrentlySelected() = currentlySelected

    @BindingAdapter("screens")
    @JvmStatic
    fun BottomNavigator.setScreens(list: List<BottomNavigatorModel.ScreenModel>) {
      screens = list
      onScreenChanged(screenId = currentlySelected)
    }
  }

  var currentlySelected: Int = INVALID_ID
    set(value) {
      onScreenChanged(screenId = value)
      updateButtons(clickedId = value)
      field = value
    }

  var onScreenChanged: ((BottomNavigatorModel.ScreenModel) -> Unit)? = null

  private var screens: List<BottomNavigatorModel.ScreenModel> = emptyList()
    set(value) {
      buildLayout(screens = value)
      field = value
    }

  private val buttons: List<ToggleButton>
    get() = getChildViews()

  init {
    orientation = HORIZONTAL
  }

  private fun buildLayout(screens: List<BottomNavigatorModel.ScreenModel>) {
    this.weightSum = screens.size.toFloat()
    screens.forEachIndexed { index, screen ->
      val button = createButton(label = screen.label)
      button.isChecked = currentlySelected == index
      button.setOnClickListener { setSelected(index) }
      addView(button, BUTTON_PARAMS)
    }
  }

  private fun createButton(label: String): ToggleButton {
    val button = LayoutInflater.from(context).inflate(
      R.layout.bottom_navigator_button,
      this,
      false
    ) as ToggleButton
    val drawable = resources.getDrawable(R.drawable.ic_ghost, null)
    button.apply {
      setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
      text = label
      textOff = label
      textOn = label
    }
    return button
  }

  private fun updateButtons(clickedId: Int) {
    buttons.forEachIndexed { index, button ->
      button.isChecked = index == clickedId
    }
  }

  private fun onScreenChanged(screenId: Int) {
    if (screens.isEmpty() || screenId == INVALID_ID) return
    onScreenChanged?.invoke(screens[screenId])
  }
}
