package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

class TopGalleryFragment : GalleryFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        activityViewModel.section = GallerySection.TOP
        setHasOptionsMenu(true)

        Log.v(TAG, "< onCreate(...)")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.v(TAG, "> onCreateOptionsMenu(...)")
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.top_section_options, menu)

        Log.v(TAG, "< onCreateOptionsMenu(...)")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.v(TAG, "> onPrepareOptionsMenu(...)")
        super.onPrepareOptionsMenu(menu)

        val selectedWindowItemId = when (activityViewModel.topWindow) {
            GalleryTopWindow.DAY -> R.id.window_day
            GalleryTopWindow.WEEK -> R.id.window_week
            GalleryTopWindow.MONTH -> R.id.window_month
            GalleryTopWindow.YEAR -> R.id.window_year
            GalleryTopWindow.ALL -> R.id.window_all
        }
        menu.findItem(selectedWindowItemId).isChecked = true

        menu.findItem(R.id.sort_rising_item).isVisible = false

        Log.v(TAG, "< onPrepareOptionsMenu(...)")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "> onOptionsItemSelected(item=$item)")

        val handled = when (item.itemId) {
            R.id.window_day, R.id.window_week, R.id.window_month, R.id.window_year, R.id.window_all -> {
                val newTopWindow = when (item.itemId) {
                    R.id.window_day -> GalleryTopWindow.DAY
                    R.id.window_week -> GalleryTopWindow.WEEK
                    R.id.window_month -> GalleryTopWindow.MONTH
                    R.id.window_year -> GalleryTopWindow.YEAR
                    R.id.window_all -> GalleryTopWindow.ALL
                    else -> GalleryViewModel.DEFAULT_GALLERY_TOP_WINDOW
                }

                Log.i(TAG, "user changed top window to $newTopWindow")
                activityViewModel.topWindow = newTopWindow

                // we need to call invalidateOptionsMenu to trigger onPrepareOptionsMenu
                // in order to update the current layout selection next time the user
                // opens this menu; as the layout menu item is always displayed on the ActionBar,
                // the system doesn't call onPrepareOptionsMenu every time the menu is clicked
                requireActivity().invalidateOptionsMenu()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        Log.v(TAG, "< onOptionsItemSelected(item=$item): $handled")
        return handled
    }

    companion object {
        private val TAG = TopGalleryFragment::class.java.simpleName
    }
}