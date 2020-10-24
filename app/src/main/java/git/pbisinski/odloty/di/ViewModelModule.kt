package git.pbisinski.odloty.di

import git.pbisinski.odloty.view.screen.dashboard.DashboardViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { DashboardViewModel() }
}
