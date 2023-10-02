package com.mutondo.weather4sureapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.mutondo.weather4sureapp.LocationService
import com.mutondo.weather4sureapp.R
import com.mutondo.weather4sureapp.databinding.ActivityMainBinding
import com.mutondo.weather4sureapp.ui.favoritelocation.FavoriteLocationFragment
import com.mutondo.weather4sureapp.ui.map.MapFragment
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.showFragment
import com.mutondo.weather4sureapp.utils.Constants.LOCATION_REQUEST_CODE
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

    override fun onResume() {
        super.onResume()
        startLocationService()
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, LocationService::class.java))
    }

    private fun startLocationService() {

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startService(Intent(this, LocationService::class.java))
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Location permission is required to get current location!")
                    .setTitle("Permission required")
                builder.setPositiveButton("OK") { dialog, id ->
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied - make request again
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
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