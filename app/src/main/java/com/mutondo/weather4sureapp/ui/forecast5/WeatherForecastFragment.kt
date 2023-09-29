package com.mutondo.weather4sureapp.ui.forecast5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.mutondo.weather4sureapp.data.ResourceStatus
import com.mutondo.weather4sureapp.databinding.FragmentWeatherForecastBinding
import com.mutondo.weather4sureapp.domain.models.Forecast
import com.mutondo.weather4sureapp.ui.BaseFragment
import com.mutondo.weather4sureapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherForecastFragment : BaseFragment() {

    private var binding: FragmentWeatherForecastBinding? = null

    @Inject
    lateinit var weatherForecastViewModel: WeatherForecastViewModel

    private lateinit var latitude: String
    private lateinit var longitude: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showActionBar()
        setActionBarTitle("Weather Forecast")
        observeWeatherForecast()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun observeWeatherForecast() {
        binding!!.progress.visibility = View.VISIBLE

        weatherForecastViewModel.getWeatherForecast(latitude, longitude, Constants.API_KEY)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    ResourceStatus.ERROR -> displayErrorMessage()
                    ResourceStatus.SUCCESS -> resource.data?.let { observeData(it) }
                }
        }
    }

    private fun observeData(forecasts: List<Forecast>) {
        binding!!.progress.visibility = View.INVISIBLE
        updateForecastView(forecasts)
    }

    private fun updateForecastView(forecasts: List<Forecast>) {
        with(binding!!) {
            val layoutManager = LinearLayoutManager(context)

            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.hasFixedSize()
            recyclerview.adapter = WeatherForecastAdapter(forecasts)
            recyclerview.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
        }
    }

    private fun displayErrorMessage() {
//        TODO("Not yet implemented")
        binding!!.progress.visibility = View.INVISIBLE
    }

    fun setData(position: LatLng) {
        latitude = position.latitude.toString()
        longitude = position.longitude.toString()
    }

    companion object {
        const val TAG = "WeatherForecastFragment"
    }
}