package git.pbisinski.odloty.di

import git.pbisinski.odloty.view.screen.dashboard.DashboardViewModel
import git.pbisinski.odloty.view.screen.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModule = module {
  viewModel { DashboardViewModel() }
  viewModel {
    SearchViewModel(
      locationRepository = get()
    )
  }
}
