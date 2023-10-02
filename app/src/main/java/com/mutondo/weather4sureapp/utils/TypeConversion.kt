package com.mutondo.weather4sureapp.utils

val Int?.orZero
    get() = this ?: 0

val Long?.orZero
    get() = this ?: 0L

val Float?.orZero: Float
    get() = this ?: 0f

val Double?.orZero: Double
    get() = this ?: 0.0