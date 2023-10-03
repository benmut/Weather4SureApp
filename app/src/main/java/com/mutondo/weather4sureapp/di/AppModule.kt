package com.mutondo.weather4sureapp.di

import com.mutondo.weather4sureapp.data.repositories.FavoriteLocationRepositoryImpl
import com.mutondo.weather4sureapp.data.repositories.GeocodeRepositoryImpl
import com.mutondo.weather4sureapp.data.repositories.WeatherRepositoryImpl
import com.mutondo.weather4sureapp.data.source.remote.GeocodeGateway
import com.mutondo.weather4sureapp.data.source.remote.GeocodeGatewayImpl
import com.mutondo.weather4sureapp.data.source.remote.WeatherGateway
import com.mutondo.weather4sureapp.data.source.remote.WeatherGatewayImpl
import com.mutondo.weather4sureapp.domain.repositories.FavoriteLocationRepository
import com.mutondo.weather4sureapp.domain.repositories.GeocodeRepository
import com.mutondo.weather4sureapp.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindWeatherGateway(implementation: WeatherGatewayImpl): WeatherGateway

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(implementation: WeatherRepositoryImpl): WeatherRepository

    @Singleton
    @Binds
    abstract fun bindFavoriteLocationRepository(implementation: FavoriteLocationRepositoryImpl): FavoriteLocationRepository

    @Singleton
    @Binds
    abstract fun bindGeocodeGateway(implementation: GeocodeGatewayImpl): GeocodeGateway

    @Singleton
    @Binds
    abstract fun bindGeocodeRepository(implementation: GeocodeRepositoryImpl): GeocodeRepository

}