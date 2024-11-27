package com.kelvin.weatherappkelvin.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.data.adapter.ForecastAdapter
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.databinding.FragmentWeatherDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details) {
    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var forecastAdapter: ForecastAdapter
    private val navArg: WeatherDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailsBinding.bind(view)
        setupRecyclerView()
        manageUiState(navArg.entity)

    }

    private fun manageUiState(result: WeatherReportEntity) {
        with(binding) {
            userLocation.text = buildString {
                append(result.locationName)
                append(", ")
                append(result.locationCountry)
            }
            windValue.text = buildString {
                append(result.wind)
                append(getString(R.string.km_hr))
            }
            humidityValue.text = buildString {
                append(result.humidity)
                append(getString(R.string.percentage_sign))
            }
            weatherImage.setAnimation(Utility.showWeatherAnimation(result.locationCondition))
            weatherImage.playAnimation()
            currentDate.text = Utility.formatTimeAndDate(result.locationTime)
            weatherFeel.text = result.locationCondition
            weatherFeelValue.text = buildString {
                append(result.temp_c)
                append(getString(R.string.celcius))
            }
            forecastAdapter.submitList(result.forecastday)

        }
    }

    private fun setupRecyclerView() {
        forecastAdapter = ForecastAdapter()
        binding.homeRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.homeRv.adapter = forecastAdapter
    }
}