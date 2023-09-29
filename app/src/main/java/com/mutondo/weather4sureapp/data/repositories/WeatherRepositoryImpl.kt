package com.mutondo.weather4sureapp.data.repositories

import com.mutondo.weather4sureapp.data.Resource
import com.mutondo.weather4sureapp.data.ResourceErrorType
import com.mutondo.weather4sureapp.data.mappers.WeatherMapper
import com.mutondo.weather4sureapp.data.source.remote.WeatherGateway
import com.mutondo.weather4sureapp.domain.repositories.WeatherRepository
import com.mutondo.weather4sureapp.domain.models.Forecast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherGateway: WeatherGateway
) : WeatherRepository {

    override suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String)
    : Resource<List<Forecast>> {
        val response = weatherGateway.getForecast5Data(latitude, longitude, apiKey)

        return if (response == null) {
            Resource.error(ResourceErrorType.NETWORK, null)
        } else {
            Resource.success(WeatherMapper.mapFrom(response))
        }
    }
}