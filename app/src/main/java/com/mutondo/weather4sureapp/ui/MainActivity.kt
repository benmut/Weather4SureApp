package com.mutondo.weather4sureapp.ui

import android.os.Bundle
import androidx.fragment.app.commit
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
}