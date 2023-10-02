package com.mutondo.weather4sureapp.ui.favoritelocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mutondo.weather4sureapp.domain.models.FavoriteLocation
import com.mutondo.weather4sureapp.domain.repositories.FavoriteLocationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteLocationViewModel @Inject constructor(
    private val repository: FavoriteLocationRepository
) : ViewModel() {

    var currentUserLatitude: Double? = null
    var currentUserLongitude: Double? = null

    fun getFavoriteLocations(): LiveData<List<FavoriteLocation>> {
        return repository.getAllFavorites()
            .asLiveData()
    }

    suspend fun saveFavoriteLocation(favorite: FavoriteLocation) {
        repository.insert(favorite)
    }
}