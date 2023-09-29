package com.mutondo.weather4sureapp.ui.forecast5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutondo.weather4sureapp.R
import com.mutondo.weather4sureapp.databinding.ItemForecastBinding
import com.mutondo.weather4sureapp.domain.models.Forecast
import com.mutondo.weather4sureapp.utils.AppUtils.Companion.convertKelvinToCelsius
import com.mutondo.weather4sureapp.utils.DayTimeUtils.Companion.getDayOfWeek

class WeatherForecastAdapter(
    private val forecasts: List<Forecast>
): RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : WeatherForecastViewHolder {
        return WeatherForecastViewHolder(ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    inner class WeatherForecastViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: Forecast) {
            binding.day.text = getDayOfWeek(forecast.timeStampLong)
            binding.dayTemp.text = convertKelvinToCelsius(forecast.temperature) // TODO - allows user to choose between C & F
            binding.icon.setImageResource(setForecastIcon(forecast.description))
        }

        private fun setForecastIcon(description: String): Int {
            return if(description.contains("sun", true)) {
                R.drawable.ic_sunny
            } else if(description.contains("clear", true)) {
                R.drawable.ic_clear_sky
            } else if(description.contains("rain", true)) {
                R.drawable.ic_rainy
            } else if(description.contains("cloud", true)) {
                R.drawable.ic_cloud
            } else if(description.contains("thunder", true)) {
                R.drawable.ic_thunder
            } else {
                R.drawable.ic_dash
            }
        }
    }
}