package com.mutondo.weather4sureapp.data.models

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list")
    val forecasts: List<Forecast> = listOf(),
    @SerializedName("city")
    val city: City? = null
)

data class Forecast(
    @SerializedName("dt")
    val timeStampL: Long? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weathers: List<Weather>? = null,
    @SerializedName("visibility")
    val visibility: Int? = null,
    @SerializedName("dt_txt")
    val timeStampS: String? = null,
)

data class Main(
    @SerializedName("temp")
    val temperature: Float? = null,
    @SerializedName("temp_min")
    val temperatureMin: Float? = null,
    @SerializedName("temp_max")
    val temperatureMax: Float? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("humidity")
    val humidity: Int? = null,
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

data class City(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("sunrise")
    val sunrise: Long? = null,
    @SerializedName("sunset")
    val sunset: Long? = null,
)