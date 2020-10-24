package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentDashboardBinding
import git.pbisinski.odloty.view.base.BaseNavigationFragment
import git.pbisinski.odloty.view.utils.DisposableVar
import git.pbisinski.odloty.view.utils.screenChanges
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DashboardFragment : BaseNavigationFragment() {

  @ExperimentalCoroutinesApi
  private val vModel: DashboardViewModel by sharedViewModel()
  private var binding: FragmentDashboardBinding by DisposableVar()

  @ExperimentalCoroutinesApi
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = binding(inflater = inflater, container = container, layoutResId = R.layout.fragment_dashboard)
    binding.viewModel = vModel
    binding.lifecycleOwner = this
    binding.bottomNavigator.attach(lifecycleOwner = viewLifecycleOwner, manager = childFragmentManager)
    return binding.root
  }

  @ExperimentalCoroutinesApi
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.run {
      vModel.state.observe { state = it }
      vModel.event.observe { handleSingleEvent(event = it) }
      intents.observe { vModel.process(intent = it) }
    }
  }

  @ExperimentalCoroutinesApi
  private val FragmentDashboardBinding.intents: Flow<DashboardIntent>
    get() {
      val tabChange = bottomNavigator.screenChanges()
        .map { DashboardIntent.GoToTab(it) }

      return merge(tabChange)
    }

  private fun handleSingleEvent(event: DashboardEvent) {
    when (event) {
      is DashboardEvent.ChangeTab -> showScreen(screen = event.tabScreen)
    }
  }
}
