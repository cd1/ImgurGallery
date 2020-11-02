package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import android.view.Menu

class TopGalleryFragment : GalleryFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        activityViewModel.section = GallerySection.TOP
        setHasOptionsMenu(true)

        Log.v(TAG, "< onCreate(...)")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.v(TAG, "> onPrepareOptionsMenu(...)")
        super.onPrepareOptionsMenu(menu)

        menu.findItem(R.id.sort_rising_item).isVisible = false

        Log.v(TAG, "< onPrepareOptionsMenu(...)")
    }

    companion object {
        private val TAG = TopGalleryFragment::class.java.simpleName
    }
}