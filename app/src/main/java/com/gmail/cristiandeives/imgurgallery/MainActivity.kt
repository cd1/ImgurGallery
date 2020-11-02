package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.gmail.cristiandeives.imgurgallery.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

    private val viewModel by viewModels<GalleryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.fragment_container)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(TOP_LEVEL_FRAGMENTS))
        navController.addOnDestinationChangedListener(this)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener(this)

        Log.v(TAG, "< onCreate(...)")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.v(TAG, "> onCreateOptionsMenu(...)")

        menuInflater.inflate(R.menu.gallery_options, menu)

        val shouldDisplayMenu = true
        Log.v(TAG, "< onCreateOptionsMenu(...): $shouldDisplayMenu")
        return shouldDisplayMenu
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        Log.v(TAG, "> onPrepareOptionsMenu(...)")

        val galleryLayout = viewModel.galleryLayout.value
            ?: GalleryViewModel.DEFAULT_GALLERY_LAYOUT
        val selectedLayoutItemId = when (galleryLayout) {
            GalleryLayout.GRID -> R.id.layout_grid_item
            GalleryLayout.LIST -> R.id.layout_list_item
            GalleryLayout.STAGGERED -> R.id.layout_staggered_item
        }
        menu.findItem(selectedLayoutItemId).isChecked = true

        val selectedSortItemId = when (viewModel.sort) {
            GallerySortBy.VIRAL -> R.id.sort_viral_item
            GallerySortBy.TOP -> R.id.sort_top_item
            GallerySortBy.TIME -> R.id.sort_time_item
            GallerySortBy.RISING -> R.id.sort_rising_item
        }
        menu.findItem(selectedSortItemId).isChecked = true

        val shouldDisplayMenu = true
        Log.v(TAG, "< onPrepareOptionsMenu(...)")
        return shouldDisplayMenu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "> onOptionsItemSelected(item=$item)")

        val handled = when (item.itemId) {
            R.id.layout_grid_item, R.id.layout_list_item, R.id.layout_staggered_item -> {
                val newLayout = when (item.itemId) {
                    R.id.layout_grid_item -> GalleryLayout.GRID
                    R.id.layout_list_item -> GalleryLayout.LIST
                    R.id.layout_staggered_item -> GalleryLayout.STAGGERED
                    else -> GalleryViewModel.DEFAULT_GALLERY_LAYOUT
                }

                Log.i(TAG, "user changed layout to $newLayout")
                viewModel.galleryLayout.value = newLayout

                // we need to call invalidateOptionsMenu to trigger onPrepareOptionsMenu
                // in order to update the current layout selection next time the user
                // opens this menu; as the layout menu item is always displayed on the ActionBar,
                // the system doesn't call onPrepareOptionsMenu every time the menu is clicked
                invalidateOptionsMenu()

                true
            }
            R.id.sort_viral_item, R.id.sort_top_item, R.id.sort_time_item, R.id.sort_rising_item -> {
                val newSort = when (item.itemId) {
                    R.id.sort_viral_item -> GallerySortBy.VIRAL
                    R.id.sort_top_item -> GallerySortBy.TOP
                    R.id.sort_time_item -> GallerySortBy.TIME
                    R.id.sort_rising_item -> GallerySortBy.RISING
                    else -> GalleryViewModel.DEFAULT_GALLERY_SORT
                }
                Log.i(TAG, "user changed sort criteria to $newSort")
                viewModel.sort = newSort

                // we need to call invalidateOptionsMenu to trigger onPrepareOptionsMenu
                // in order to update the current layout selection next time the user
                // opens this menu; as the layout menu item is always displayed on the ActionBar,
                // the system doesn't call onPrepareOptionsMenu every time the menu is clicked
                invalidateOptionsMenu()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        Log.v(TAG, "< onOptionsItemSelected(item=$item): $handled")
        return handled
    }

    override fun onBackPressed() {
        Log.v(TAG, "> onBackPressed()")

        finish()

        Log.v(TAG, "< onBackPressed()")
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        Log.v(TAG, "> onDestinationChanged(...)")

        Log.i(TAG, "user changed section to $destination")

        Log.v(TAG, "< onDestinationChanged(...)")
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        Log.v(TAG, "> onNavigationItemReselected(item=$item)")

        Log.i(TAG, "user scrolled current section (${item.title}) to the top")

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            ?.childFragmentManager
            ?.fragments
            ?.first()
        if (currentFragment is GalleryFragment) {
            currentFragment.scrollContentToTop()
        }

        Log.v(TAG, "< onNavigationItemReselected(item=$item)")
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private val TOP_LEVEL_FRAGMENTS = setOf(
            R.id.hot_section,
            R.id.top_section,
            R.id.user_section
        )
    }
}