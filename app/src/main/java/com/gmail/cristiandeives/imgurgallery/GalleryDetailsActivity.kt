package com.gmail.cristiandeives.imgurgallery

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs

class GalleryDetailsActivity : AppCompatActivity(R.layout.activity_gallery_details) {
    private val args by navArgs<GalleryDetailsActivityArgs>()

    override fun onAttachFragment(fragment: Fragment) {
        Log.v(TAG, "> onAttachFragment(fragment=$fragment)")

        // pass down the arguments from the Activity to its fragment
        fragment.arguments = args.toBundle()

        Log.v(TAG, "< onAttachFragment(fragment=$fragment)")
    }

    companion object {
        private val TAG = GalleryDetailsActivity::class.java.simpleName
    }
}