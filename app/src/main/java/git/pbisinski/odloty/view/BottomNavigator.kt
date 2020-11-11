package git.pbisinski.odloty.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import git.pbisinski.odloty.R
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner

import git.pbisinski.odloty.view.model.BottomTabModel

class BottomNavigator @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), LifecycleObserver, FragmentManager.OnBackStackChangedListener {

  companion object {
    private val BUTTON_PARAMS = LayoutParams(0, LayoutParams.MATCH_PARENT, 1F)

    @JvmStatic
    @BindingAdapter("tabs")
    fun BottomNavigator.setModels(tabs: List<BottomTabModel>) {
      buildLayout(tabs = tabs)
    }
  }

  private var fragmentManager: FragmentManager? = null
  private val buttonsList: HashMap<String, AppCompatButton> = hashMapOf()

  var onNavigation: ((Screen) -> Unit)? = null
    set(value) {
      field = value
      onBackStackChanged()
    }

  init {
    orientation = HORIZONTAL
  }

  fun attach(lifecycleOwner: LifecycleOwner, manager: FragmentManager) {
    fragmentManager = manager
    lifecycleOwner.lifecycle.addObserver(this)
    manager.addOnBackStackChangedListener(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun detach() {
    fragmentManager?.removeOnBackStackChangedListener(this)
    fragmentManager = null
    buttonsList.clear()
    onNavigation = null
  }

  override fun onBackStackChanged() {
    fragmentManager?.run {
      val lastFragIndex = (backStackEntryCount - 1).takeIf { it >= 0 } ?: return
      val visibleFragName = getBackStackEntryAt(lastFragIndex).name ?: error("Couldn't get frag name!")
      updateSelection(screenLabel = visibleFragName)
    }
  }

  private fun buildLayout(tabs: List<BottomTabModel>) {
    weightSum = tabs.size.toFloat()
    tabs.forEach { model ->
      val button = createButton(model = model)
      button.setOnClickListener { view ->
        if (!view.isSelected) {
          onNavigation?.invoke(model.screen)
          updateSelection(screenLabel = model.screen.name)
        }
      }
      addView(button, BUTTON_PARAMS)
      buttonsList[model.screen.name] = button
    }
  }

  private fun updateSelection(screenLabel: String) {
    buttonsList.forEach { (id, view) -> view.isSelected = id == screenLabel }
  }

  private fun createButton(model: BottomTabModel): AppCompatButton {
    val button = LayoutInflater.from(context).inflate(
      R.layout.bottom_navigator_button,
      this,
      false
    ) as AppCompatButton
    val drawable = resources.getDrawable(model.iconResId, null)
    button.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
    button.text = model.label
    return button
  }
}
