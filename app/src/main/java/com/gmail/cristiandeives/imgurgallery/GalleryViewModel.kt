package com.gmail.cristiandeives.imgurgallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.cristiandeives.imgurgallery.data.AsyncCallback
import com.gmail.cristiandeives.imgurgallery.data.Repository

class GalleryViewModel : ViewModel() {
    private val _galleries = MutableLiveData<Resource<List<Gallery>>>()
    val galleries: LiveData<Resource<List<Gallery>>> = _galleries

    var section = GallerySection.HOT
        set(value) {
            field = value

            readGalleries()
        }

    fun readGalleries() {
        _galleries.value = Resource.Loading()

        Repository.getGalleryImages(section, object : AsyncCallback<List<Gallery>> {
            override fun onSuccess(response: List<Gallery>) {
                _galleries.value = Resource.Success(response)
            }

            override fun onError(ex: Exception?) {
                _galleries.value = Resource.Error(ex)
            }
        })
    }
}