package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

class UserGalleryFragment : GalleryFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        activityViewModel.section = GallerySection.USER
        setHasOptionsMenu(true)

        Log.v(TAG, "< onCreate(...)")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.v(TAG, "> onCreateOptionsMenu(...)")
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.user_section_options, menu)

        Log.v(TAG, "< onCreateOptionsMenu(...)")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.v(TAG, "> onPrepareOptionsMenu(menu=$menu)")
        super.onPrepareOptionsMenu(menu)

        menu.findItem(R.id.show_viral).isChecked = activityViewModel.shouldShowViral

        Log.v(TAG, "< onPrepareOptionsMenu(menu=$menu)")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "> onOptionsItemSelected(item=$item)")

        val handled = when (item.itemId) {
            R.id.show_viral -> {
                val newShowViral = !activityViewModel.shouldShowViral
                activityViewModel.shouldShowViral = newShowViral

                Log.i(TAG, "user changed show viral state to $newShowViral")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        Log.v(TAG, "< onOptionsItemSelected(item=$item): $handled")
        return handled
    }

    companion object {
        private val TAG = UserGalleryFragment::class.java.simpleName
    }
}