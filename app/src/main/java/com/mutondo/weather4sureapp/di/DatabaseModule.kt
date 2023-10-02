package com.mutondo.weather4sureapp.di

import android.content.Context
import com.mutondo.weather4sureapp.data.source.local.Weather4SureDatabase
import com.mutondo.weather4sureapp.data.source.local.daos.FavoriteLocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FavoriteLocationDao {
        return Weather4SureDatabase.getInstance(appContext).favorites()
    }
}