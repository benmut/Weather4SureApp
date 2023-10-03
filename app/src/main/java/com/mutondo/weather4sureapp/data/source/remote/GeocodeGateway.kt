package com.mutondo.weather4sureapp.data.source.remote

import com.mutondo.weather4sureapp.BuildConfig.MAPS_API_KEY
import com.mutondo.weather4sureapp.data.apiservice.GeocodeService
import com.mutondo.weather4sureapp.data.models.GeocodeResponse
import retrofit2.Retrofit
import javax.inject.Inject

interface GeocodeGateway {
    suspend fun getGeocodeData(latLng: String): GeocodeResponse?
}

class GeocodeGatewayImpl @Inject constructor(
    retrofit: Retrofit
): GeocodeGateway {

    private val service = retrofit.create(GeocodeService::class.java)

    override suspend fun getGeocodeData(latLng: String): GeocodeResponse? {
        return service.getGeocodeData("https://maps.googleapis.com/maps/api/geocode/json?latlng=${latLng}&key=${MAPS_API_KEY}")
    }
}