package com.mutondo.weather4sureapp.data.repositories

import com.mutondo.weather4sureapp.data.source.local.daos.FavoriteLocationDao
import com.mutondo.weather4sureapp.data.source.local.mappers.FavoriteLocationMapper
import com.mutondo.weather4sureapp.domain.models.FavoriteLocation
import com.mutondo.weather4sureapp.domain.repositories.FavoriteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteLocationRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteLocationDao

) : FavoriteLocationRepository {

    override fun getAllFavorites(): Flow<List<FavoriteLocation>> {
        return favoriteDao.getAllFavorites()
            .map { favoriteEntities ->
                val favorites = mutableListOf<FavoriteLocation>()
                for (favorite in favoriteEntities) {
                    favorites.add(FavoriteLocationMapper.mapFrom(favorite))
                }

                favorites
            }
    }

    override suspend fun insert(favorite: FavoriteLocation) {
        return favoriteDao.insert(
            FavoriteLocationMapper.mapTo(favorite)
        )
    }
}