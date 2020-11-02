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

        val context = requireContext()
        val galleryLayout = GridLayoutManager(context, 2)
        val galleryAdapter = GalleryAdapter()

        binding.resultRecycler.apply {
            layoutManager = galleryLayout
            adapter = galleryAdapter
        }
        binding.tryAgainButton.setOnClickListener(this)
        binding.swipeLayout.setOnRefreshListener(this)

        activityViewModel.apply {
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
                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
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

    companion object {
        private val TAG = GalleryFragment::class.java.simpleName
    }
}