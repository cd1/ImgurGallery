package com.gmail.cristiandeives.imgurgallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.gmail.cristiandeives.imgurgallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "> onCreate(...)")
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navController = findNavController(R.id.fragment_container)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(TOP_LEVEL_FRAGMENTS))
        navController.addOnDestinationChangedListener(this)

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

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private val TOP_LEVEL_FRAGMENTS = setOf(
            R.id.hot_section,
            R.id.top_section,
            R.id.user_section
        )
    }
}