package com.mutondo.weather4sureapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mutondo.weather4sureapp.databinding.FragmentMapBinding

class MapFragment : BaseFragment(), OnMapReadyCallback, OnMapLongClickListener {

    private var binding: FragmentMapBinding? = null
    lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.mapView?.onCreate(savedInstanceState)
        binding?.mapView?.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onPause() {
        binding?.mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.mapView?.onDestroy()
        binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        googleMap.setOnMapLongClickListener(this)

        //TODO - Set it to current location instead
        val homeLatLng = LatLng(-26.2041, 28.0473) // Johannesburg
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, ZOOM_LEVEL))
    }

    override fun onMapLongClick(point: LatLng) {
        val latLng = LatLng(point.latitude, point.longitude)
        addMarker(map, latLng)
    }

    private fun addMarker(map: GoogleMap, latLng: LatLng) {
        map.addMarker(
            MarkerOptions()
                .position(latLng)
        )
    }

    companion object {
        const val TAG = "MapFragment"
        const val ZOOM_LEVEL = 10f
    }
}