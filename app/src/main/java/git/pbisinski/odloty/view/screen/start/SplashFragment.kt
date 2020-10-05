package git.pbisinski.odloty.view.screen.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentSplashBinding
import git.pbisinski.odloty.view.base.BaseFragment

class SplashFragment : BaseFragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    createBinding<FragmentSplashBinding>(
      layoutResId = R.layout.fragment_splash,
      inflater = inflater,
      container = container
    )
}
