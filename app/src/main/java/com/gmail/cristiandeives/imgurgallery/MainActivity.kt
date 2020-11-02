package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navController = findNavController(R.id.fragment_container)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(TOP_LEVEL_FRAGMENTS))
        navController.addOnDestinationChangedListener(this)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener(this)

        Log.v(TAG, "< onCreate(...)")
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