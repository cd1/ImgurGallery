package com.gmail.cristiandeives.imgurgallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.cristiandeives.imgurgallery.databinding.GalleryViewHolderBinding

class GalleryAdapter : RecyclerView.Adapter<GalleryViewHolder>() {
    var galleries = emptyList<Gallery>()
        set(value) {
            val diff = DiffUtil.calculateDiff(DiffCallback(field, value))
            field = value

            diff.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GalleryViewHolderBinding.inflate(inflater, parent, false)

        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val data = galleries[position]
        holder.title = data.title
        Glide.with(holder.context)
                .load(data.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.gallery_background)
                .error(R.drawable.gallery_error_background)
                .into(holder.imageView)

        holder.imageView.setOnClickListener {
            val args = GalleryDetailsActivityArgs(
                data.imageUrl, data.title, data.description, data.upvotes, data.downvotes, data.score
            )
            holder.imageView.findNavController().navigate(R.id.gallery_details_activity, args.toBundle())
        }
    }

    override fun getItemCount() = galleries.size

    companion object {
        private class DiffCallback(val oldData: List<Gallery>, val newData: List<Gallery>) : DiffUtil.Callback() {
            override fun getOldListSize() = oldData.size

            override fun getNewListSize() = newData.size

            override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
                oldData[oldPosition].id == newData[newPosition].id

            override fun areContentsTheSame(oldPosition: Int, newPosition: Int) =
                oldData[oldPosition] == newData[newPosition]
        }
    }
}