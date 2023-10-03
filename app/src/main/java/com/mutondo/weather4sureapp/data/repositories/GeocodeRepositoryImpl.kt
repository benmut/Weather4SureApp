package com.mutondo.weather4sureapp.data.repositories

import com.mutondo.weather4sureapp.data.Resource
import com.mutondo.weather4sureapp.data.ResourceErrorType
import com.mutondo.weather4sureapp.data.mappers.GeocodeMapper
import com.mutondo.weather4sureapp.data.source.remote.GeocodeGateway
import com.mutondo.weather4sureapp.domain.models.Geocode
import com.mutondo.weather4sureapp.domain.repositories.GeocodeRepository
import javax.inject.Inject

class GeocodeRepositoryImpl @Inject constructor(
    private val geocodeGateway: GeocodeGateway
) : GeocodeRepository {

    override suspend fun getGeocodeData(latLng: String): Resource<List<Geocode>> {
        val response = geocodeGateway.getGeocodeData(latLng)

        return if (response == null) {
            Resource.error(ResourceErrorType.NETWORK, null)
        } else {
            Resource.success(GeocodeMapper.mapFrom(response))
        }
    }
}