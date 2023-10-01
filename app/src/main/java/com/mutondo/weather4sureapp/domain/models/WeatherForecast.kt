package com.mutondo.weather4sureapp.domain.models

data class Forecast(
    val temp: Float,
    val tempMax: Float,
    val tempMin: Float,
    val description: String,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val timeStampLong: Long,
    val timeStampString: String,
    val city: String,
    val sunrise: Long,
    val sunset: Long
)