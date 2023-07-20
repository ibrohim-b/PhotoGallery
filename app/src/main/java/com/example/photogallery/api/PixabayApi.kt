package com.example.photogallery.api

import retrofit2.Call
import retrofit2.http.GET

interface PixabayApi {

    @GET("/")
    fun fetchContents(): Call<String>

    @GET(
        "/api/?key=38301013-0f7890efe13f18c42e8f2a4d4&image_type=photo&pretty=true&page=1"
    )
    fun fetchPhotos(): Call<PhotoResponse>
}