package com.mutondo.weather4sureapp.domain.models

data class Forecast(
    val temperature: Float,
    val timeStampLong: Long,
    val description: String,
    val timeStampString: String
)