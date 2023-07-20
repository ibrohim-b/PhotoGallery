package com.example.photogallery.api

import com.google.gson.annotations.SerializedName

data class GalleryItem(
    var user: String = "",
    var id: String = "",
    @SerializedName("userImageURL") var url: String = ""
)
