package com.mutondo.weather4sureapp.data.mappers

import com.mutondo.weather4sureapp.data.models.GeocodeResponse
import com.mutondo.weather4sureapp.domain.models.Geocode
import com.mutondo.weather4sureapp.utils.orZero

object GeocodeMapper {
    fun mapFrom(response: GeocodeResponse?): List<Geocode> {
        return response?.results?.map {
            Geocode(
                longName = it.addresses[0].longName.orEmpty(),
                shortName = it.addresses[0].shortName.orEmpty(),
                types = it.addresses[0].types,
                formattedAddress = it.formattedAddress.orEmpty(),
                latitude = it.geometry?.location?.latitude.orZero,
                longitude = it.geometry?.location?.longitude.orZero,
            )
        } ?: listOf()
    }
}