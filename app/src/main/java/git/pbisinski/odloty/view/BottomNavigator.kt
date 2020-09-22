package git.pbisinski.odloty.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleObserver
import git.pbisinski.odloty.R
import git.pbisinski.odloty.BR
import git.pbisinski.odloty.databinding.BottomNavigatorButtonBinding
import git.pbisinski.odloty.view.screen.dashboard.BottomNavigatorModel

class BottomNavigator @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
  LifecycleObserver,
  FragmentManager.OnBackStackChangedListener,
  Observer<BottomNavigatorModel.BottomButtonModel> {

  companion object {
    private val BUTTON_PARAMS = LayoutParams(0, LayoutParams.MATCH_PARENT, 1F)
    private const val NO_CONTAINER = -1
  }

  private lateinit var currentlySelected: MutableLiveData<BottomNavigatorModel.BottomButtonModel>
  private var screens: List<BottomNavigatorModel.BottomButtonModel> = emptyList()

  private var containerResId: Int = NO_CONTAINER
  private var fragmentManager: FragmentManager? = null

  init {
    orientation = HORIZONTAL
  }

  fun attach(fragment: Fragment, @IdRes containerId: Int, model: BottomNavigatorModel) {
    setupObservers(fragment = fragment, model = model)
    fragmentManager = fragment.childFragmentManager
    containerResId = containerId
    screens = model.screens
    currentlySelected = model.selectedStream
    buildLayout(screens = model.screens, lifecycleOwner = fragment.viewLifecycleOwner)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun detach() {
    fragmentManager?.removeOnBackStackChangedListener(this)
    fragmentManager = null
  }

  override fun onBackStackChanged() {
    fragmentManager?.run {
      val selectedTag = getBackStackEntryAt(backStackEntryCount - 1).name
      if (currentlySelected.value?.label == selectedTag) return
      val selectedScreen = screens.find { screen -> screen.label == selectedTag } ?: error("Couldn't find screen!")
      currentlySelected.value = selectedScreen
    }
  }

  override fun onChanged(model: BottomNavigatorModel.BottomButtonModel?) {
    model ?: return
    fragmentManager?.showScreen(screen = model.screen, containerResId = containerResId)
  }

  private fun buildLayout(screens: List<BottomNavigatorModel.BottomButtonModel>, lifecycleOwner: LifecycleOwner) {
    weightSum = screens.size.toFloat()
    screens.forEach { model ->
      val button = createButton(screenModel = model, lifecycleOwner = lifecycleOwner)
      button.setOnClickListener { currentlySelected.value = model }
      addView(button, BUTTON_PARAMS)
    }
  }

  private fun createButton(
    screenModel: BottomNavigatorModel.BottomButtonModel,
    lifecycleOwner: LifecycleOwner
  ): ToggleButton {
    val binding = BottomNavigatorButtonBinding.inflate(LayoutInflater.from(context))
    binding.setVariable(BR.model, screenModel)
    binding.lifecycleOwner = lifecycleOwner
    val button = binding.root as ToggleButton
    val drawable = resources.getDrawable(R.drawable.ic_ghost, null)
    button.apply {
      setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
    }
    return button
  }

  private fun setupObservers(fragment: Fragment, model: BottomNavigatorModel) {
    fragment.viewLifecycleOwner.lifecycle.addObserver(this)
    fragment.childFragmentManager.addOnBackStackChangedListener(this)
    model.selectedStream.observe(fragment.viewLifecycleOwner, this)
  }
}
