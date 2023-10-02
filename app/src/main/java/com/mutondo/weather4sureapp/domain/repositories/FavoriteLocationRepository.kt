package com.mutondo.weather4sureapp.domain.repositories

import com.mutondo.weather4sureapp.domain.models.FavoriteLocation
import kotlinx.coroutines.flow.Flow

interface FavoriteLocationRepository {
    fun getAllFavorites(): Flow<List<FavoriteLocation>>
    suspend fun insert(favorite: FavoriteLocation)
}