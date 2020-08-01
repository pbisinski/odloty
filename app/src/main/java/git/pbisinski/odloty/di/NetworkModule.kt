package git.pbisinski.odloty.di

import git.pbisinski.odloty.OdlotyApplication
import git.pbisinski.odloty.api.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
  single<OkHttpClient> { okHttp(authInterceptor = get(named<AuthInterceptor>())) }
  single<Retrofit> { retrofit(client = get()) }
  single<Interceptor>(named<AuthInterceptor>()) { AuthInterceptor() }
}

fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
  .baseUrl(OdlotyApplication.URL)
  .client(client)
  .addConverterFactory(GsonConverterFactory.create())
  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
  .build()

fun okHttp(authInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
  .connectTimeout(30, TimeUnit.SECONDS)
  .addInterceptor(authInterceptor)
  .protocols(listOf(Protocol.HTTP_1_1))
  .readTimeout(30, TimeUnit.SECONDS)
  .writeTimeout(30, TimeUnit.SECONDS)
  .build()