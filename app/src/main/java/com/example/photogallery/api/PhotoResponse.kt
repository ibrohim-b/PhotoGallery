package com.example.photogallery.api

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("hits")
    lateinit var galleryItems: List<GalleryItem>
}