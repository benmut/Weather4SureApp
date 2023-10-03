package com.mutondo.weather4sureapp.domain.repositories

import com.mutondo.weather4sureapp.data.Resource
import com.mutondo.weather4sureapp.domain.models.Geocode

interface GeocodeRepository {
    suspend fun getGeocodeData(latLng: String): Resource<List<Geocode>>
}