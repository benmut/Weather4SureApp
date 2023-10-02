package com.mutondo.weather4sureapp

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.isGpsEnabled
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.isNetworkEnabled

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    private fun start() {

        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(isGpsEnabled(locationManager).not() && isNetworkEnabled(locationManager).not()) {
            // This should be handled different for example prompt the user to enable them in the Settings
            return
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000).apply {// 5 seconds for testing purposes
                setMinUpdateDistanceMeters(50f)
        }.build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)

                result.lastLocation?.let {
                    Log.i(TAG, "Current LatLng: ${it.latitude}, ${it.longitude} ")

                    val bundle = Bundle().also { bundle ->
                        bundle.putDouble(LAT_KEY, it.latitude)
                        bundle.putDouble(LNG_KEY, it.longitude)
                    }

                    val intent = Intent(packageName.toString() + "MY_LOCATION")
                        .also { intent -> intent.putExtras(bundle) }

                    sendBroadcast(intent)
                }
            }
        }

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    private fun stop() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        stopForeground(STOP_FOREGROUND_DETACH)
    }

    companion object {
        const val LAT_KEY = "lat_key"
        const val LNG_KEY = "lng_key"
        const val TAG = "LocationService"
    }
}