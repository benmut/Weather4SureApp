package com.mutondo.weather4sureapp.data.source.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mutondo.weather4sureapp.data.source.local.entities.FavoriteLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteLocationDao {

    @Query("SELECT * FROM ${FavoriteLocationEntity.TABLE_NAME}")
    fun getAllFavorites(): Flow<List<FavoriteLocationEntity>>

    @Query("SELECT * FROM ${FavoriteLocationEntity.TABLE_NAME} WHERE name = :name")
    fun getFavorite(name: String): Flow<FavoriteLocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteLocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg favorites: FavoriteLocationEntity): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(favorite: FavoriteLocationEntity): Int

    @Delete
    suspend fun delete(favorite: FavoriteLocationEntity)

    @Query("DELETE FROM ${FavoriteLocationEntity.TABLE_NAME}")
    suspend fun deleteAllFavorites()
}