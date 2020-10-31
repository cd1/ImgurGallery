package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gmail.cristiandeives.imgurgallery.databinding.FragmentGalleryDetailsBinding

class GalleryDetailsFragment : Fragment() {
    private val args by navArgs<GalleryDetailsActivityArgs>()
    private lateinit var binding: FragmentGalleryDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.v(TAG, "> onCreateView(..)")

        binding = FragmentGalleryDetailsBinding.inflate(inflater, container, false)

        val view = binding.root
        Log.v(TAG, "< onCreateView(..): $view")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "> onViewCreated(...)")
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            Glide.with(requireContext())
                .load(args.imageUrl)
                .placeholder(R.drawable.gallery_background)
                .error(R.drawable.gallery_error_background)
                .into(binding.image)

            title.text = args.title
            description.text = args.description
            upvotes.text = args.upvotes.toString()
            downvotes.text = args.downvotes.toString()
            score.text = args.score.toString()

            // hide the description view if it's empty
            if (args.description.isEmpty()) {
                description.visibility = View.GONE
            }
        }

        Log.v(TAG, "< onViewCreated(...)")
    }

    companion object {
        private val TAG = GalleryDetailsFragment::class.java.simpleName
    }
}