package com.mutondo.weather4sureapp.data.models

import com.google.gson.annotations.SerializedName

data class GeocodeResponse(
    @SerializedName("results")
    val results: List<Result> = listOf()
)

data class Result(
    @SerializedName("address_components")
    val addresses: List<Address> = listOf(),
    @SerializedName("formatted_address")
    val formattedAddress: String? = null,
    @SerializedName("geometry")
    val geometry: Geometry? = null
)

data class Address(
    @SerializedName("long_name")
    val longName: String? = null,
    @SerializedName("short_name")
    val shortName: String? = null,
    @SerializedName("types")
    val types: List<String> = listOf()
)

data class Geometry(
    @SerializedName("location")
    val location: Location? = null
)

data class Location(
    @SerializedName("lat")
    val latitude: Double? = null,
    @SerializedName("lng")
    val longitude: Double? = null
)