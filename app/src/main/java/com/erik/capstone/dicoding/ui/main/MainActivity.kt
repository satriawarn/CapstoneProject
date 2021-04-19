package com.erik.capstone.dicoding.ui.main

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.erik.capstone.dicoding.R
import com.erik.capstone.dicoding.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_fav -> {
                    val uriNav = Uri.parse("captsone://favorite")
                    navController.navigate(uriNav)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detail_activity) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }

        binding.navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (isTaskRoot
            && navHostFragment.childFragmentManager.backStackEntryCount == 0
            && supportFragmentManager.backStackEntryCount == 0
        ) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(
                { doubleBackToExitPressedOnce = false },
                2000
            )
        } else {
            super.onBackPressed()
        }
    }
}