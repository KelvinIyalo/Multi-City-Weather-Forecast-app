package com.kelvin.weatherappkelvin.presentation.home_root

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
        manageBottomNavBar(navController)
    }

    private fun manageBottomNavBar(navController: NavController) {

        with(binding.navView) {
            setupWithNavController(navController)
            navController.addOnDestinationChangedListener { nav, destination, _ ->

                visibility = when (destination.id) {
                    R.id.navigation_home , R.id.navigation_favorite-> {

                        View.VISIBLE
                    }

                    else -> {
                        View.GONE
                    }
                }

            }
        }

    }
}