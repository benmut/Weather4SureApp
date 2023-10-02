package com.mutondo.weather4sureapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.mutondo.weather4sureapp.R
import com.mutondo.weather4sureapp.databinding.ActivityMainBinding
import com.mutondo.weather4sureapp.ui.favoritelocation.FavoriteLocationFragment
import com.mutondo.weather4sureapp.ui.map.MapFragment
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.showFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        setUpNavigationDrawer()

        if (savedInstanceState == null) {
            val mapFragment = MapFragment()

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, mapFragment, MapFragment.TAG)
            }
        }
    }

    private fun setUpNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBarMain.toolbar,
            R.string.open_navigation_drawer, R.string.close_navigation_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.bringToFront()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

            R.id.nav_map -> {
                val mapFragment = MapFragment()
                showFragment(this, R.id.fragment_container, mapFragment, MapFragment.TAG)
            }

            R.id.nav_favorites -> {
                var favoriteLocationFragment = supportFragmentManager.findFragmentByTag(FavoriteLocationFragment.TAG)
                if(favoriteLocationFragment == null) {
                    favoriteLocationFragment = FavoriteLocationFragment()
                }
                showFragment(this, R.id.fragment_container, favoriteLocationFragment, FavoriteLocationFragment.TAG)
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> {
                showTemperatureSettings()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    private var checkedItem = 0
    private fun showTemperatureSettings() {
        val temperatureUnits = arrayOf("Celsius", "Fahrenheit")

        AlertDialog.Builder(this)
            .setTitle("Temperature Unit")
            .setPositiveButton("Save") { dialog, which ->
                saveTemperatureUnit(temperatureUnits[checkedItem])
            }
            .setNegativeButton("Cancel") { dialog, which ->

            }
            .setSingleChoiceItems(temperatureUnits, checkedItem) { dialog, which ->
                checkedItem = which
            }
            .create()
            .show()
    }

    private fun saveTemperatureUnit(unit: String) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        with (sharedPref.edit()) {
            putString(getString(R.string.temperature_unit_key), unit)
            apply()
        }
    }
}