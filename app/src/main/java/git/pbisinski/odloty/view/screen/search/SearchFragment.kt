package git.pbisinski.odloty.view.screen.search

import android.os.Bundle
import android.util.Log
import android.view.View
import git.pbisinski.odloty.R
import git.pbisinski.odloty.api.repository.LocalisationRepository
import git.pbisinski.odloty.databinding.FragmentSearchBinding
import git.pbisinski.odloty.view.base.BaseFragment
import git.pbisinski.odloty.view.screen.start.SplashScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

  override val layoutIdRes: Int = R.layout.fragment_search

  private val disposable = CompositeDisposable()

  private val localisationRepository: LocalisationRepository by inject()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupListeners()
    binding.textviewSecond.text = this.toString()
  }

  private fun setupListeners() {
    binding.buttonDownload.setOnClickListener {
      localisationRepository.getPlaces(market = "PL", currency = "PLN", locale = "pl-PL", query = "Warsaw")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { response -> "downloaded ${response.size} places" }
        .subscribeBy(
          onSuccess = { responseText -> binding.textviewFirst.text = responseText },
          onError = { error -> Log.e(javaClass.simpleName, "download error", error) }
        ).addTo(disposable)
    }
    binding.buttonNavigate.setOnClickListener {
      navigator.showScreen(screen = SplashScreen)
    }
  }

  override fun onStop() {
    disposable.clear()
    super.onStop()
  }
}
