package git.pbisinski.odloty

import android.app.Application
import git.pbisinski.odloty.di.networkModule
import git.pbisinski.odloty.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OdlotyApplication : Application() {

  companion object {
    const val URL = BuildConfig.API_URL
    const val SECRET = BuildConfig.API_KEY
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@OdlotyApplication)
      modules(
        networkModule,
        repositoryModule
      )
    }
  }
}
