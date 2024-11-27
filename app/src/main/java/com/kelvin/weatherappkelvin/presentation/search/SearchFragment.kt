package com.kelvin.weatherappkelvin.presentation.search

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.common.Utility.startSlideInAnimation
import com.kelvin.weatherappkelvin.common.handler.UiState
import com.kelvin.weatherappkelvin.common.showKeyboardOnFocus
import com.kelvin.weatherappkelvin.data.adapter.SearchForecastAdapter
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var searchForecastAdapter: SearchForecastAdapter
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private val searchScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        setupSharedElementTransition()
        setupRecyclerView()
        setupSearch()
        setupBackButton()

    }

    private fun showProgress(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setupRecyclerView() {
        searchForecastAdapter = SearchForecastAdapter(
            onItemClicked = ::manageItemClick,
            onClickSaveToFavorite = ::saveFavorite
        )
        binding.searchListRv.apply {
            layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = searchForecastAdapter
        }
    }

    private fun searchWeather(query: String) {
        viewModel.searchWeatherByName(query).observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Loading -> showProgress(true)
                is UiState.Success -> {
                    showProgress(false)
                    searchForecastAdapter.submitList(listOf(response.data))
                }

                is UiState.DisplayError -> {
                    showProgress(false)
                    Utility.showMessage(response.error, binding.root)
                }
            }
        }
    }

    private fun manageItemClick(entity: WeatherReportEntity) {
        val direction =
            SearchFragmentDirections.actionSearchFragmentToWeatherDetailsFragment(
                entity = entity
            )
        findNavController().navigate(direction)
    }

    private fun saveFavorite(entity: WeatherReportEntity) {
        viewModel.saveFavorite(entity)
    }

    private fun debounceSearch(query: String) {
        searchScope.launch {
            delay(800) // Wait for 800ms after the last user input
            searchWeather(query)
        }
    }


    private fun setupSharedElementTransition() {
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun setupSearch() {
        with(binding.searchEt) {
            requestFocus()
            showKeyboardOnFocus(requireContext())
            addTextChangedListener { editable ->
                val query = editable?.toString()?.trim()
                if (!query.isNullOrEmpty() && query.length >= 3) {
                    debounceSearch(query)
                }
            }
        }
    }

    private fun setupBackButton() {
        with(binding.backBtn) {
            startSlideInAnimation()
            setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}

