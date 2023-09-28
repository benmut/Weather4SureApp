package com.mutondo.weather4sureapp.di

import com.mutondo.weather4sureapp.data.source.remote.WeatherGateway
import com.mutondo.weather4sureapp.data.source.remote.WeatherGatewayImpl
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
}