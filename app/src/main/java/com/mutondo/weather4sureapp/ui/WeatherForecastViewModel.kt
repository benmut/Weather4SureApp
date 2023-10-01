package com.mutondo.weather4sureapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mutondo.weather4sureapp.data.Resource
import com.mutondo.weather4sureapp.data.ResourceStatus
import com.mutondo.weather4sureapp.domain.models.Forecast
import com.mutondo.weather4sureapp.domain.repositories.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherForecastViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    lateinit var forecasts: List<Forecast>

    fun getWeatherForecast(latitude: String, longitude: String, appId: String)
    : LiveData<Resource<List<Forecast>>> {
        return liveData {
            val resource = weatherRepository.getForecast5Data(latitude, longitude, appId)

            when (resource.status) {
                ResourceStatus.ERROR -> {
                    emit(Resource.error(resource.errorType, null))
                }
                ResourceStatus.SUCCESS -> {
                    emit(resource)
                }
            }
        }
    }
}