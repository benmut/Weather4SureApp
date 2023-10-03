package com.mutondo.weather4sureapp.domain.models

data class Geocode(
    val longName: String,
    val shortName: String,
    val types: List<String> = listOf(),
    val formattedAddress: String,
    val latitude: Double,
    val longitude: Double
)