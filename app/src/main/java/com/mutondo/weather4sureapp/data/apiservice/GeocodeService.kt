package com.mutondo.weather4sureapp.data.apiservice

import com.mutondo.weather4sureapp.data.models.GeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface GeocodeService {
    @GET
    suspend fun getGeocodeData(@Url url: String): GeocodeResponse?
}