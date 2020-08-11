package git.pbisinski.odloty.view.screen.start

import android.os.Bundle
import android.view.View
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentSplashBinding
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.screen.dashboard.DashboardScene

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

  override val layoutIdRes: Int = R.layout.fragment_splash

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.buttonNavigate.setOnClickListener {
      navigator.openScene(scene = DashboardScene)
    }
  }
}
