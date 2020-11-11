package git.pbisinski.odloty.di

import git.pbisinski.odloty.OdlotyApplication
import git.pbisinski.odloty.api.AuthInterceptor
import git.pbisinski.odloty.api.LogInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
  single<OkHttpClient> {
    okHttp(
      authInterceptor = get(named<AuthInterceptor>()),
      logInterceptor = get(named<LogInterceptor>())
    )
  }
  single<Retrofit> { retrofit(client = get()) }
  single<Interceptor>(named<AuthInterceptor>()) { AuthInterceptor() }
  single<Interceptor>(named<LogInterceptor>()) { LogInterceptor() }
}

fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
  .baseUrl(OdlotyApplication.URL)
  .client(client)
  .addConverterFactory(GsonConverterFactory.create())
  .build()

fun okHttp(
  authInterceptor: Interceptor,
  logInterceptor: Interceptor
): OkHttpClient = OkHttpClient.Builder()
  .connectTimeout(TIMEOUT_CONNECT_SEC, TimeUnit.SECONDS)
  .addInterceptor(authInterceptor)
  .addInterceptor(logInterceptor)
  .protocols(listOf(Protocol.HTTP_1_1))
  .readTimeout(TIMEOUT_READ_SEC, TimeUnit.SECONDS)
  .writeTimeout(TIMEOUT_WRITE_SEC, TimeUnit.SECONDS)
  .build()

private const val TIMEOUT_CONNECT_SEC = 30L
private const val TIMEOUT_READ_SEC = 30L
private const val TIMEOUT_WRITE_SEC = 30L
