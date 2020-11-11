package git.pbisinski.odloty.api

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

class LogInterceptor : Interceptor {

  companion object {
    private const val GET = "GET --> "
    private const val RETURN = "RETURN --> "
    private const val HEADER = "HEADER"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val response = chain.proceed(request)
    log(text = request.url().toString(), prefix = GET)
    logHeaders(headers = request.headers())
    log(text = readBody(response = response), prefix = RETURN)
    return response
  }

  private fun logHeaders(headers: Headers) {
    headers.names().map { name ->
      val values = headers.values(name).joinToString()
      log(text = "$name: $values", prefix = HEADER)
    }
  }

  private fun readBody(response: Response): String {
    val source = response.body()?.source() ?: return ""
    source.request(Long.MAX_VALUE)
    val bodyString = source.buffer.snapshot().utf8()
    return JSONObject(bodyString).toString(2)
  }

  private fun log(text: String, prefix: String? = null) {
    val textToPrint = prefix?.let { prefixText -> "$prefixText $text" } ?: text
    Log.d(javaClass.simpleName, textToPrint)
  }
}
