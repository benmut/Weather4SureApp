package com.mutondo.weather4sureapp.utils

import kotlin.math.roundToInt

class AppUtils {
    companion object {

        fun convertKelvinToCelsius(kelvin: Float): String {
            return (kelvin - 273.15f).roundToInt().toString() + "\u00B0"
        }
    }
}