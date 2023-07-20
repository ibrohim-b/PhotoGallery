package com.example.photogallery

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photogallery.api.GalleryItem
import com.example.photogallery.api.PhotoResponse
import com.example.photogallery.api.PixabayApi
import com.example.photogallery.api.PixabayResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "PixabayFetcher"
class PixabayFetcher(val context: Context) {
    private val pixabayApi: PixabayApi

    init {
        val mockInterceptor = MockApiInterceptor(context = context)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(mockInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.pixabay.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pixabayApi = retrofit.create(PixabayApi::class.java)
    }


    fun fetchPhotos(): LiveData<List<GalleryItem>> {
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        val pixabayRequest: Call<PhotoResponse> = pixabayApi.fetchPhotos()
        pixabayRequest.enqueue(object : Callback<PhotoResponse> {
            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(
                call: Call<PhotoResponse>,
                response: Response<PhotoResponse>
            ) {
                Log.d(TAG, "Response received ${response.body()}")
                val pixabayResponse: PhotoResponse? = response.body()
                val photoResponse: PhotoResponse? = pixabayResponse
                var galleryItems: List<GalleryItem> = photoResponse?.galleryItems
                    ?: mutableListOf()
                galleryItems = galleryItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItems
            }
        })
            return responseLiveData
    }
}