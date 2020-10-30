package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log

class TopGalleryFragment : GalleryFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        activityViewModel.section = GallerySection.TOP

        Log.v(TAG, "< onCreate(...)")
    }

    companion object {
        private val TAG = TopGalleryFragment::class.java.simpleName
    }
}