package com.mutondo.weather4sureapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mutondo.weather4sureapp.data.source.local.daos.FavoriteLocationDao
import com.mutondo.weather4sureapp.data.source.local.entities.FavoriteLocationEntity

@Database(entities = [FavoriteLocationEntity::class], version = 1)
abstract class Weather4SureDatabase : RoomDatabase() {

    abstract fun favorites(): FavoriteLocationDao

    companion object {

        @Volatile
        private var instance: Weather4SureDatabase? = null

        fun getInstance(context: Context): Weather4SureDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): Weather4SureDatabase {
            val dbBuilder = Room.databaseBuilder(
                context.applicationContext, Weather4SureDatabase::class.java, DATABASE_NAME
            )

            return dbBuilder.build()
        }

        private const val DATABASE_NAME = "Weather4SureDatabase"

    }
}