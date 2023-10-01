package com.mutondo.weather4sureapp.data.mappers

import com.mutondo.weather4sureapp.data.models.ForecastResponse
import com.mutondo.weather4sureapp.domain.models.Forecast
import com.mutondo.weather4sureapp.utils.orZero

object WeatherMapper {

    fun mapFrom(response: ForecastResponse?): List<Forecast> {
        return response?.forecasts?.filter { f ->
            // I choose to pick 12:00:00 data... I could pick based on current time as well
            f.timeStampS!!.contains("12:00:00") }?.map {
            Forecast(
                temp = it.main?.temperature.orZero,
                tempMax = it.main?.temperatureMax.orZero,
                tempMin = it.main?.temperatureMin.orZero,
                description = it.weathers?.get(0)?.description.orEmpty(),
                pressure = it.main?.pressure.orZero,
                humidity = it.main?.humidity.orZero,
                visibility = it.visibility.orZero,
                timeStampLong = it.timeStampL.orZero,
                timeStampString = it.timeStampS.toString(),

                city = response.city?.name.orEmpty(),
                sunrise = response.city?.sunrise.orZero,
                sunset = response.city?.sunset.orZero
            )
        } ?: listOf()
    }
}