package com.codefresher.allinone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.codefresher.allinone.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        window.statusBarColor = this.getColor(R.color.white)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }


    }
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNAV)
//        val bottomNavigationView = mainBinding.bottomNAV
        // costomise start selected item
        bottomNavigationView.selectedItemId = R.id.nav_home

        val navGraphIds = listOf(R.navigation.nav_home, R.navigation.nav_favorite, R.navigation.nav_create,
        R.navigation.nav_account)

        // setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.mainContainer,
            intent = intent
        )

        currentNavController = controller

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false || super.onSupportNavigateUp()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // now that BottomNavigationBar has resoted its instance state
        setupBottomNavigationBar()
    }

}