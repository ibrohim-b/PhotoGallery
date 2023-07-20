package com.example.photogallery

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.photogallery.api.GalleryItem

class PhotoGalleryViewModel(application: Application) : AndroidViewModel(application) {

    val galleryItemLiveData: LiveData<List<GalleryItem>>

    init {
        galleryItemLiveData = PixabayFetcher(application).fetchPhotos()
    }
}