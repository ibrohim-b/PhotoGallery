package com.example.photogallery

import com.example.photogallery.api.PixabayResponse
import android.content.Context
import android.provider.MediaStore.Audio.Media
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MockApiInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (chain.request().url().toString().contains(
                "api/?key=38301013-0f7890efe13f18c42e8f2a4d4&image_type=photo&pretty=true&page=1")){
            val json = readJSONFromAsset("mock_fetch_photos_response.json")
          chain.proceed(chain.request()).newBuilder().code(200).protocol(Protocol.HTTP_1_1).body(
              ResponseBody.create(MediaType.parse("application/json"), json)
          ).message("Ok").build()
        } else {
            chain.proceed(chain.request())
        }
    }

    @Throws(IOException::class)
    private fun readJSONFromAsset(fileName: String?): String {
        var json = "{}"
        try {
            val inputStream: InputStream? = fileName?.let { context.assets?.open(it) }
            json = inputStream?.bufferedReader().use { it!!.readText() }
        } catch (exception: Exception) {
            Log.d(READ_FILE_JSON, exception.message.toString())
        }
        return json
    }

    companion object {
        private const val READ_FILE_JSON = "READ_FILE_JSON"
    }
}
