package com.mutondo.weather4sureapp.ui.favoritelocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.mutondo.weather4sureapp.data.Resource
import com.mutondo.weather4sureapp.data.ResourceStatus
import com.mutondo.weather4sureapp.domain.models.FavoriteLocation
import com.mutondo.weather4sureapp.domain.models.Geocode
import com.mutondo.weather4sureapp.domain.repositories.FavoriteLocationRepository
import com.mutondo.weather4sureapp.domain.repositories.GeocodeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteLocationViewModel @Inject constructor(
    private val locationRepository: FavoriteLocationRepository,
    private val geocodeRepository: GeocodeRepository
) : ViewModel() {

    var currentUserLatitude: Double? = null
    var currentUserLongitude: Double? = null

    fun getFavoriteLocations(): LiveData<List<FavoriteLocation>> {
        return locationRepository.getAllFavorites()
            .asLiveData()
    }

    suspend fun saveFavoriteLocation(favorite: FavoriteLocation) {
        locationRepository.insert(favorite)
    }

    fun getGeocodeData(latLng: String): LiveData<Resource<List<Geocode>>> {
        return liveData {
            val resource = geocodeRepository.getGeocodeData(latLng)

            when (resource.status) {
                ResourceStatus.ERROR -> {
                    emit(Resource.error(resource.errorType, null))
                }
                ResourceStatus.SUCCESS -> {
                    emit(resource)
                }
            }
        }
    }
}