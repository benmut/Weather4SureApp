package com.mutondo.weather4sureapp.domain.repositories

import com.mutondo.weather4sureapp.data.Resource
import com.mutondo.weather4sureapp.domain.models.Forecast

interface WeatherRepository {
    suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String): Resource<List<Forecast>>
}