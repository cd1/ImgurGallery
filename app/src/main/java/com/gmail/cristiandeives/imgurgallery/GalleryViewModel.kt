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

            if (sort == GallerySortBy.RISING && section != GallerySection.USER) {
                sort = DEFAULT_GALLERY_SORT
            } else {
                readGalleries()
            }
        }

    var sort = DEFAULT_GALLERY_SORT
        set(value) {
            field = value

            readGalleries()
        }

    var galleryLayout = MutableLiveData<GalleryLayout>().apply {
        value = DEFAULT_GALLERY_LAYOUT
    }

    var shouldShowViral: Boolean = true
        set(value) {
            field = value

            readGalleries()
        }

    fun readGalleries() {
        _galleries.value = Resource.Loading()

        Repository.getGalleryImages(section, sort, shouldShowViral, object : AsyncCallback<List<Gallery>> {
            override fun onSuccess(response: List<Gallery>) {
                _galleries.value = Resource.Success(response)
            }

            override fun onError(ex: Exception?) {
                _galleries.value = Resource.Error(ex)
            }
        })
    }

    companion object {
        val DEFAULT_GALLERY_LAYOUT = GalleryLayout.GRID
        val DEFAULT_GALLERY_SORT = GallerySortBy.VIRAL
    }
}