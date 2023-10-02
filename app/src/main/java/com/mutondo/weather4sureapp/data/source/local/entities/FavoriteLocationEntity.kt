package com.mutondo.weather4sureapp.data.source.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = FavoriteLocationEntity.TABLE_NAME,
    indices = [Index("name")])
data class FavoriteLocationEntity(
    @PrimaryKey(autoGenerate = true)
    val favoriteId: Long = 0,
    val name: String,
    @Embedded
    val location: LocationColumns
) {
    companion object {
        const val TABLE_NAME = "favorite_location"
    }
}

data class LocationColumns(
    val latitude: Double,
    val longitude: Double
)