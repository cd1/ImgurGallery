package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gmail.cristiandeives.imgurgallery.databinding.FragmentGalleryBinding

open class GalleryFragment : Fragment(),
    View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentGalleryBinding
    protected val activityViewModel by activityViewModels<GalleryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.v(TAG, "> onCreateView(...)")

        binding = FragmentGalleryBinding.inflate(inflater, container, false)

        val view = binding.root
        Log.v(TAG, "< onCreateView(...): $view")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "> onViewCreated(...)")
        super.onViewCreated(view, savedInstanceState)

        val galleryAdapter = GalleryAdapter()

        binding.resultRecycler.adapter = galleryAdapter
        binding.tryAgainButton.setOnClickListener(this)
        binding.swipeLayout.setOnRefreshListener(this)

        activityViewModel.apply {
            galleryLayout.observe(viewLifecycleOwner) { layout: GalleryLayout? ->
                Log.v(TAG, "> galleryLayout#observe(layout=$layout)")

                layout?.let { updateGalleryLayout(it) }

                Log.v(TAG, "< galleryLayout#observe(layout=$layout)")
            }

            galleries.observe(viewLifecycleOwner) { res: Resource<List<Gallery>>? ->
                Log.v(TAG, "> galleries#observe(res=$res)")

                when (res) {
                    is Resource.Loading -> {
                        binding.errorView.visibility = View.GONE
                        if (binding.resultRecycler.childCount > 0) {
                            binding.resultRecycler.visibility = View.VISIBLE
                            binding.swipeLayout.isRefreshing = true
                        } else {
                            binding.resultRecycler.visibility = View.GONE
                            binding.progressView.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Success -> {
                        binding.progressView.visibility = View.GONE
                        binding.errorView.visibility = View.GONE
                        binding.resultRecycler.visibility = View.VISIBLE

                        binding.swipeLayout.isRefreshing = false

                        res.data?.let { galleryAdapter.galleries = it }
                    }
                    is Resource.Error -> {
                        binding.progressView.visibility = View.GONE
                        binding.resultRecycler.visibility = View.GONE
                        binding.errorView.visibility = View.VISIBLE

                        binding.swipeLayout.isRefreshing = true

                        res.exception?.consume()?.message?.let { errorMsg ->
                            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                Log.v(TAG, "< galleries#observe(res=$res)")
            }
        }

        Log.v(TAG, "< onViewCreated(...)")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.try_again_button -> activityViewModel.readGalleries()
        }
    }

    override fun onRefresh() {
        Log.i(TAG, "user refreshed the data")
        activityViewModel.readGalleries()
    }

    fun scrollContentToTop() {
        binding.resultRecycler.smoothScrollToPosition(0)
    }

    private fun updateGalleryLayout(layout: GalleryLayout) {
        binding.resultRecycler.apply {
            layoutManager = when (layout) {
                GalleryLayout.GRID -> GridLayoutManager(requireContext(), 2)
                GalleryLayout.LIST -> LinearLayoutManager(requireContext())
                GalleryLayout.STAGGERED -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }

            // this is needed so the views are correctly reloaded...
            adapter = adapter
        }
    }

    companion object {
        private val TAG = GalleryFragment::class.java.simpleName
    }
}