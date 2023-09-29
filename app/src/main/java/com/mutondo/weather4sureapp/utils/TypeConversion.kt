package com.mutondo.weather4sureapp.utils

val Long?.orZero
    get() = this ?: 0L

val Float?.orZero: Float
    get() = this ?: 0f