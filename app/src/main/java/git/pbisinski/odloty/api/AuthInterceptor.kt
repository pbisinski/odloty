package git.pbisinski.odloty.api

import git.pbisinski.odloty.OdlotyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

  companion object {
    private const val HEADER_HOST = "x-rapidapi-host"
    private const val HEADER_KEY = "x-rapidapi-key"
    private val HEADER_HOST_VALUE = OdlotyApplication.URL.substringAfter(delimiter = "://")
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = chain.request().newBuilder()
    builder.header(HEADER_HOST, HEADER_HOST_VALUE)
    builder.header(HEADER_KEY, OdlotyApplication.SECRET)
    return chain.proceed(builder.build())
  }
}