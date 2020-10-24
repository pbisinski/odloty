package git.pbisinski.odloty.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import git.pbisinski.odloty.R
import git.pbisinski.odloty.view.screen.dashboard.BottomScreenModel

class BottomNavigator @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), LifecycleObserver, FragmentManager.OnBackStackChangedListener {

  companion object {
    private val BUTTON_PARAMS = LayoutParams(0, LayoutParams.MATCH_PARENT, 1F)
  }

  private var fragmentManager: FragmentManager? = null
  private val buttonsList: HashMap<String, AppCompatButton> = hashMapOf()
  private var onNavigation: ((Screen) -> Unit)? = null

  init {
    orientation = HORIZONTAL
  }

  fun attach(fragment: Fragment, screenModels: List<BottomScreenModel>, onNavigate: (Screen) -> Unit) {
    fragmentManager = fragment.childFragmentManager
    onNavigation = onNavigate
    buildLayout(models = screenModels)
    setupObservers(fragment = fragment)
    onBackStackChanged()
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

  private fun buildLayout(models: List<BottomScreenModel>) {
    weightSum = models.size.toFloat()
    models.forEach { model ->
      val button = createButton(model = model)
      button.setOnClickListener { view ->
        if (!view.isSelected) {
          onNavigation?.invoke(model.screen)
          view.isSelected = true
        }
      }
      addView(button, BUTTON_PARAMS)
      buttonsList[model.screen.name] = button
    }
  }

  private fun updateSelection(screenLabel: String) {
    buttonsList.forEach { (id, view) -> view.isSelected = id == screenLabel }
  }

  private fun createButton(model: BottomScreenModel): AppCompatButton {
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

  private fun setupObservers(fragment: Fragment) {
    fragment.viewLifecycleOwner.lifecycle.addObserver(this)
    fragmentManager?.addOnBackStackChangedListener(this)
  }
}
