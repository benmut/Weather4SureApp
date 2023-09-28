package com.mutondo.weather4sureapp.data.models

import com.google.gson.annotations.SerializedName

class ForecastResponse {
    @SerializedName("list")
    val temperatures: List<Forecast> = listOf()
}

data class Forecast(
    @SerializedName("dt")
    val timeStampL: Long? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weathers: List<Weather>? = null,
    @SerializedName("dt_txt")
    val timeStampS: String? = null,
)

data class Main(
    @SerializedName("temp")
    val temperature: Float? = null,
    @SerializedName("temp_max")
    val temperatureMax: Float? = null,
    @SerializedName("temp_min")
    val temperatureMin: Float? = null,
)

data class Weather(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("main")
    val main: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("icon")
    val icon: String? = null
)