package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log

class UserGalleryFragment : GalleryFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        activityViewModel.section = GallerySection.USER

        Log.v(TAG, "< onCreate(...)")
    }

    companion object {
        private val TAG = UserGalleryFragment::class.java.simpleName
    }
}