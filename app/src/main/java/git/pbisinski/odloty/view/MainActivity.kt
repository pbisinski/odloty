package git.pbisinski.odloty.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import git.pbisinski.odloty.R
import git.pbisinski.odloty.api.repository.LocalisationRepository
import git.pbisinski.odloty.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val localisationRepository: LocalisationRepository by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    setupListeners()
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
        )
    }
  }
}
