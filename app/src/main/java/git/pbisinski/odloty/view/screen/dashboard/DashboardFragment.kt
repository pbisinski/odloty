package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import git.pbisinski.odloty.BR
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentDashboardBinding
import git.pbisinski.odloty.view.Screen
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.screen.search.SearchScreen
import git.pbisinski.odloty.view.screen.start.SplashScreen
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

  override val layoutIdRes: Int = R.layout.fragment_dashboard

  private val vm: DashboardViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = super.onCreateView(inflater, container, savedInstanceState)
    binding.setVariable(BR.viewModel, vm)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.bottomNavigator.attach(
      fragment = this,
      containerId = binding.navigationContainer.id,
      model = vm.bottomNavigatorModel
    )
    binding.textviewFirst.text = this.toString()
  }

  override fun backPressed(): Boolean {
    Log.d(javaClass.simpleName, vm.bottomNavigatorModel.selectedStream.value!!.label)
    val entryCount = childFragmentManager.backStackEntryCount
    val canPop = entryCount > 1
    if (canPop) {
      childFragmentManager.popBackStack()
    }
    return canPop
  }
}

class BottomNavigatorModel(
  def: List<Screen>
) : BaseObservable() {

  val selectedStream = MutableLiveData<BottomButtonModel>()
  val screens: List<BottomButtonModel> = def.map { screen ->
      BottomButtonModel(
        screen = screen,
        label = screen.javaClass.simpleName,
        selectedStream = selectedStream
      )
    }

  init {
    selectedStream.value = screens.first()
  }

  class BottomButtonModel(
    val screen: Screen,
    val label: String,
    selectedStream: LiveData<BottomButtonModel>
  ) {
    val isSelected: LiveData<Boolean> = Transformations.map(selectedStream) { model -> model == this }
  }
}

class DashboardViewModel : ViewModel() {

  val bottomNavigatorModel = BottomNavigatorModel(def = listOf(SplashScreen, SearchScreen))
}
