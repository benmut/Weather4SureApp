package com.mutondo.weather4sureapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.mutondo.weather4sureapp.R
import com.mutondo.weather4sureapp.databinding.ActivityMainBinding
import com.mutondo.weather4sureapp.ui.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        if (savedInstanceState == null) {
            val mapFragment = MapFragment()

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, mapFragment, MapFragment.TAG)
            }
        }
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