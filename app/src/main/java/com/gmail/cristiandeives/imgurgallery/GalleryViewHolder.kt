package com.gmail.cristiandeives.imgurgallery

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.gmail.cristiandeives.imgurgallery.databinding.GalleryViewHolderBinding

class GalleryViewHolder(private val binding: GalleryViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    val context: Context
        get() = binding.title.context

    var title: String
        get() = binding.title.text.toString()

        set(value) {
            binding.title.text = value
        }

    val imageView = binding.image
}