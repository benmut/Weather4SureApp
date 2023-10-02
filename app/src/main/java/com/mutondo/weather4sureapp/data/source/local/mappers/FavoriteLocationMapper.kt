package com.mutondo.weather4sureapp.data.source.local.mappers

import com.mutondo.weather4sureapp.data.source.local.entities.FavoriteLocationEntity
import com.mutondo.weather4sureapp.data.source.local.entities.LocationColumns
import com.mutondo.weather4sureapp.domain.models.FavoriteLocation
import com.mutondo.weather4sureapp.utils.orZero

object FavoriteLocationMapper {

    fun mapTo(favorite: FavoriteLocation): FavoriteLocationEntity {
        return FavoriteLocationEntity(
            name = favorite.name,
            location = LocationColumns(favorite.latitude, favorite.longitude)
        )
    }

    fun mapFrom(favoriteEntity: FavoriteLocationEntity?): FavoriteLocation {
        return favoriteEntity.let {
            FavoriteLocation(
                name = it?.name.orEmpty(),
                latitude = it?.location?.latitude.orZero,
                longitude = it?.location?.longitude.orZero
            )
        }
    }
}