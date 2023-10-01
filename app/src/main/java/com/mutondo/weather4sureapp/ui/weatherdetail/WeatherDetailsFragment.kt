package com.mutondo.weather4sureapp.ui.weatherdetail

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.mutondo.weather4sureapp.R
import com.mutondo.weather4sureapp.databinding.FragmentWeatherDetailsBinding
import com.mutondo.weather4sureapp.ui.BaseFragment
import com.mutondo.weather4sureapp.ui.WeatherForecastViewModel
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.convertKelvinToCelsius
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.convertKelvinToFahrenheit
import com.mutondo.weather4sureapp.utils.DayTimeUtils.Companion.getTimeFromTimestamp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment(), OnSharedPreferenceChangeListener {

    private var binding: FragmentWeatherDetailsBinding? = null
    private var position: Int = 0

    @Inject
    lateinit var viewModel: WeatherForecastViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideActionBar()
        bindViews()
    }

    private fun bindViews() {
        with(binding!!) {
            cityName.text = viewModel.forecasts[position].city
            weatherDescription.text = viewModel.forecasts[position].description
            sunrise.text = getTimeFromTimestamp(viewModel.forecasts[position].sunrise)
            sunset.text = getTimeFromTimestamp(viewModel.forecasts[position].sunset)

            val humidityValue = "${viewModel.forecasts[position].humidity}%"
            humidity.text = humidityValue

            val pressureValue = "${viewModel.forecasts[position].pressure} hPa"
            pressure.text = pressureValue

            val visibilityValue = "${viewModel.forecasts[position].visibility / 1000.0} km"
            visibility.text = visibilityValue
        }

        val unit = PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString(getString(R.string.temperature_unit_key), "")

        if(unit.equals(String()) || unit.equals("Celsius", true)) {
            bindCelsiusTemperatures()
        } else {
            bindFahrenheitTemperatures()
        }
    }

    private fun bindCelsiusTemperatures() {
        binding?.temperature?.text = convertKelvinToCelsius(viewModel.forecasts[position].temp)
        binding?.minTemp?.text = convertKelvinToCelsius(viewModel.forecasts[position].tempMin)
        binding?.maxTemp?.text = convertKelvinToCelsius(viewModel.forecasts[position].tempMax)
    }

    private fun bindFahrenheitTemperatures() {
        binding?.temperature?.text = convertKelvinToFahrenheit(viewModel.forecasts[position].temp)
        binding?.minTemp?.text = convertKelvinToFahrenheit(viewModel.forecasts[position].tempMin)
        binding?.maxTemp?.text = convertKelvinToFahrenheit(viewModel.forecasts[position].tempMax)
    }

    fun setData(position: Int) {
        this.position = position
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        when(sharedPreferences?.getString(key, "")) {
            "Celsius" -> bindCelsiusTemperatures()
            "Fahrenheit" -> bindFahrenheitTemperatures()
        }
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val TAG = "WeatherDetailsFragment"
    }
}