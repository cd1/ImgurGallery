package com.gmail.cristiandeives.imgurgallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gmail.cristiandeives.imgurgallery.databinding.GalleryViewHolderBinding

class GalleryAdapter : RecyclerView.Adapter<GalleryViewHolder>() {
    var galleries = emptyList<Gallery>()
        set(value) {
            val diff = DiffUtil.calculateDiff(DiffCallback(field, value))
            field = value

            diff.dispatchUpdatesTo(this)
        }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        recyclerView = rv
    }

    override fun onDetachedFromRecyclerView(rv: RecyclerView) {
        recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GalleryViewHolderBinding.inflate(inflater, parent, false)

        // grid items are square and list and staggered items need to span the entire image height,
        // so we need to update the ImageView layout accordingly
        val shouldAdjustImageSize: Boolean
        val imageRatio: String
        if (currentGalleryLayout() == GalleryLayout.GRID) {
            shouldAdjustImageSize = false
            imageRatio = "1:1"
        } else {
            shouldAdjustImageSize = true
            imageRatio = ""
        }
        binding.image.apply {
            adjustViewBounds = shouldAdjustImageSize
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                dimensionRatio = imageRatio
            }
        }

        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val data = galleries[position]
        holder.title = data.title
        var glideRequestBuilder = Glide.with(holder.context)
                .load(data.smallImageUrl)
                .placeholder(R.drawable.gallery_background)
                .error(R.drawable.gallery_error_background)

        // only crop images for grid items; list and staggered layout use images in their original ratio
        if (currentGalleryLayout() == GalleryLayout.GRID) {
            glideRequestBuilder = glideRequestBuilder.centerCrop()
        }

        glideRequestBuilder.into(holder.imageView)

        holder.imageView.setOnClickListener {
            val args = GalleryDetailsActivityArgs(
                data.imageUrl, data.title, data.description, data.upvotes, data.downvotes, data.score
            )
            holder.imageView.findNavController().navigate(R.id.gallery_details_activity, args.toBundle())
        }
    }

    override fun getItemCount() = galleries.size

    // convert the actual RecyclerView layout to the GalleryLayout enum
    private fun currentGalleryLayout() = when (recyclerView?.layoutManager) {
        is GridLayoutManager -> GalleryLayout.GRID
        is LinearLayoutManager -> GalleryLayout.LIST
        is StaggeredGridLayoutManager -> GalleryLayout.STAGGERED
        else -> GalleryViewModel.DEFAULT_GALLERY_LAYOUT
    }

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