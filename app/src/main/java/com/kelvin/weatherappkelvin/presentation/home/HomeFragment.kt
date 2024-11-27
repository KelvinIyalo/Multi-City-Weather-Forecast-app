package com.kelvin.weatherappkelvin.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.common.handler.UiState
import com.kelvin.weatherappkelvin.data.adapter.ForecastAdapter
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setupRecyclerView()
        weatherObserver()
        navigateToSearch()
    }

    private fun weatherObserver() {
        viewModel.getWeather().observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Loading -> showProgress(true)
                is UiState.Success -> {
                    showProgress(false)
                    manageUiState(response.data)
                }
                is UiState.DisplayError -> {
                    showProgress(false)
                    Utility.showMessage(response.error, binding.root)
                }
            }
        }

    }

    private fun showProgress(isLoading: Boolean) {
        binding.progress.isVisible = isLoading
    }

    private fun setupRecyclerView() {
        forecastAdapter = ForecastAdapter()
        with(binding.homeRv) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
        }
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

    private fun navigateToSearch(){
        binding.searchEt.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.searchEt to "search_search")
            val direction = HomeFragmentDirections.actionNavigationHomeToSearchFragment()
            findNavController().navigate(direction, extras)
        }
    }
}