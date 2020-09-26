package git.pbisinski.odloty.view.screen.dashboard

import android.os.Bundle
import android.view.View
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentDashboardBinding
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.screen.search.SearchScreen

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

  override val layoutIdRes: Int = R.layout.fragment_dashboard

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupListeners()
  }

  private fun setupListeners() {
    binding.buttonNavigate.setOnClickListener {
      navigator.showScreen(screen = SearchScreen)
    }
  }
}
