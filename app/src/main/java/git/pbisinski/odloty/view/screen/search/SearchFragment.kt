package git.pbisinski.odloty.view.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentSearchBinding
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.utils.DisposableVar
import git.pbisinski.odloty.view.utils.clicks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment() {

  @ExperimentalCoroutinesApi
  private val vModel: SearchViewModel by sharedViewModel()
  private var binding: FragmentSearchBinding by DisposableVar()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = binding(
      inflater = inflater,
      container = container,
      layoutResId = R.layout.fragment_search
    )
    binding.lifecycleOwner = this
    return binding.root
  }

  @ExperimentalCoroutinesApi
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.run {
      viewScope.launchWhenStarted {
        vModel.state
          .onEach { state = it }
          .collect()
      }
      viewScope.launchWhenStarted {
        vModel.event
          .onEach { handleSingleEvent(it) }
          .collect()
      }
      viewScope.launchWhenStarted {
        intents
          .onEach { vModel.process(it) }
          .collect()
      }
    }
  }

  @ExperimentalCoroutinesApi
  private val FragmentSearchBinding.intents: Flow<SearchIntent>
    get() = merge(
      buttonNavigate.clicks().map { SearchIntent.Route.GoToSplash },
      buttonDownload.clicks().map { SearchIntent.Download }
    )

  private fun handleSingleEvent(event: SearchEvent) {
    when (event) {
      is SearchEvent.RouteTo -> navigator.showScreen(screen = event.destination)
    }
  }
}
