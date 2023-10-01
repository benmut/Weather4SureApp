package com.mutondo.weather4sureapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import kotlin.math.roundToInt

class AppUtils {
    companion object {

        fun convertKelvinToCelsius(kelvin: Float): String {
            return (kelvin - 273.15f).roundToInt().toString() + "\u00B0"
        }

        fun convertKelvinToFahrenheit(kelvin: Float): String {
            return ((kelvin - 273.15f) * 9/5 + 32).roundToInt().toString() + "\u00B0"
        }

        fun showFragment(activity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment, tag: String) {
            activity.supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(containerViewId, fragment, tag)
                addToBackStack(null)
            }
        }
    }
}