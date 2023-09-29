package com.mutondo.weather4sureapp.data.mappers

import com.mutondo.weather4sureapp.data.models.ForecastResponse
import com.mutondo.weather4sureapp.domain.models.Forecast
import com.mutondo.weather4sureapp.utils.orZero

object WeatherMapper {

    fun mapFrom(response: ForecastResponse?): List<Forecast> {
        return response?.temperatures?.filter { forecast ->
            // Picking the temperature for 12:00
            forecast.timeStampS.toString().contains("12:00") }?.map {
            Forecast(
                temperature = it.main?.temperature.orZero,
                timeStampLong = it.timeStampL.orZero,
                description = it.weathers?.get(0)?.main.orEmpty(),
                timeStampString = it.timeStampS.toString()
            )
        } ?: listOf()
    }
}