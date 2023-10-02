package com.mutondo.weather4sureapp.ui.map

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.mutondo.weather4sureapp.BuildConfig
import com.mutondo.weather4sureapp.LocationService
import com.mutondo.weather4sureapp.R
import com.mutondo.weather4sureapp.databinding.FragmentMapBinding
import com.mutondo.weather4sureapp.domain.models.FavoriteLocation
import com.mutondo.weather4sureapp.ui.BaseFragment
import com.mutondo.weather4sureapp.ui.favoritelocation.FavoriteLocationViewModel
import com.mutondo.weather4sureapp.ui.forecast5.WeatherForecastFragment
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.showFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : BaseFragment(), OnMapReadyCallback, OnMapLongClickListener, OnInfoWindowLongClickListener {

    private var binding: FragmentMapBinding? = null
    lateinit var map: GoogleMap

    private val markers = mutableListOf<Marker>()
    private lateinit var receiver: BroadcastReceiver

    @Inject
    lateinit var viewModel: FavoriteLocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarTitle("Map")

        binding?.mapView?.onCreate(savedInstanceState)
        binding?.mapView?.getMapAsync(this)

        initializePlaceClient()
        setupAutocompleteSupportFragment()
    }

    private fun initializePlaceClient() {
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), BuildConfig.MAPS_API_KEY)
        }

        Places.createClient(requireContext())
    }

    private fun setupAutocompleteSupportFragment() {
        val autocompleteSupportFragment =
            childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment?

        autocompleteSupportFragment?.setPlaceFields(listOf(Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteSupportFragment?.setOnPlaceSelectedListener(placeSelectionListener)
    }

    private val placeSelectionListener: PlaceSelectionListener
        get() = object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.e(TAG, "An error occurred: $status")
            }

            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: ${place.name}, LatLng: ${place.latLng}" )
                addMarker(map, place.latLng!!)

                saveFavoriteLocation(place.name!!, place.latLng!!.latitude, place.latLng!!.longitude)
            }
        }

    private fun saveFavoriteLocation(name: String, latitude: Double, longitude: Double) {
        lifecycleScope.launch {
            val favoriteLocation = FavoriteLocation(name, latitude, longitude)
            viewModel.saveFavoriteLocation(favoriteLocation)
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()

        val filter = IntentFilter(activity?.packageName.toString() + "MY_LOCATION")
        receiver = MyBroadcastReceiver()
        activity?.registerReceiver(receiver, filter)
    }

    override fun onPause() {
        binding?.mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(receiver)

        binding?.mapView?.onDestroy()
        binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        googleMap.setOnMapLongClickListener(this)
        googleMap.setOnInfoWindowLongClickListener(this)

        if(requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }
        map.uiSettings.isZoomControlsEnabled = true

        if(viewModel.currentUserLatitude != null && viewModel.currentUserLongitude != null) {
            val currentLatLng = LatLng(viewModel.currentUserLatitude!!, viewModel.currentUserLongitude!!)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_LEVEL))
        }

    }

    override fun onMapLongClick(point: LatLng) {
        val latLng = LatLng(point.latitude, point.longitude)

        removeMarkers(markers)
        addMarker(map, latLng)?.let { markers.add(it) }
    }

    override fun onInfoWindowLongClick(marker: Marker) {

        val weatherForecastFragment = WeatherForecastFragment()
        weatherForecastFragment.setData(marker.position)
        showFragment(requireActivity(), R.id.fragment_container, weatherForecastFragment, WeatherForecastFragment.TAG)
    }

    private fun addMarker(map: GoogleMap, latLng: LatLng): Marker? {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))

        return map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("You are here")
                .snippet("Lat: ${latLng.latitude}; Lng: ${latLng.longitude}")
        )
    }

    private fun removeMarkers(list: MutableList<Marker>) {
        for (marker: Marker in list) {
            marker.remove()
        }
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if(intent?.action == activity?.packageName.toString() + "MY_LOCATION") {

                // Get current Lat & Lng or default to Jhb
                viewModel.currentUserLatitude = intent.extras?.getDouble(LocationService.LAT_KEY, -26.2041)
                viewModel.currentUserLongitude = intent.extras?.getDouble(LocationService.LNG_KEY, 28.0473)

                val currentLatLng = LatLng(viewModel.currentUserLatitude!!, viewModel.currentUserLongitude!!)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_LEVEL))
            }
        }
    }

    companion object {
        const val TAG = "MapFragment"
        const val ZOOM_LEVEL = 10f
    }
}