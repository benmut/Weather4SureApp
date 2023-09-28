package com.mutondo.weather4sureapp.data.apiservice

import com.mutondo.weather4sureapp.data.models.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    suspend fun getForecast5Data(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("apiKey") apiKey: String): ForecastResponse?
}