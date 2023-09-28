package com.mutondo.weather4sureapp.data.source.remote

import com.mutondo.weather4sureapp.data.apiservice.WeatherService
import com.mutondo.weather4sureapp.data.models.ForecastResponse
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherGateway {
    suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String): ForecastResponse?
}

@Singleton
class WeatherGatewayImpl @Inject constructor(
    retrofit: Retrofit
): WeatherGateway {

    private val service = retrofit.create(WeatherService::class.java)

    override suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String)
    : ForecastResponse? {
        return service.getForecast5Data(latitude, longitude, apiKey)
    }
}