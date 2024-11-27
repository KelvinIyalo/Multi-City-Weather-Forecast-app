package com.kelvin.weatherappkelvin.presentation.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.common.handler.UiState
import com.kelvin.weatherappkelvin.data.adapter.SearchForecastAdapter
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.databinding.FragmentFavoriteBinding
import com.kelvin.weatherappkelvin.presentation.dialog.showAlertDialog
import com.kelvin.weatherappkelvin.presentation.search.SearchFragmentDirections
import com.kelvin.weatherappkelvin.presentation.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private lateinit var searchForecastAdapter: SearchForecastAdapter
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        setupRecyclerView()
        favoriteWeatherObserver()

    }

    private fun favoriteWeatherObserver() {
        viewModel.getAllFavoriteWeatherReport()
            .observe(viewLifecycleOwner, Observer { response ->
                searchForecastAdapter.submitList(response)
                handleEmptyState(response.isEmpty())
            })
    }

    private fun setupRecyclerView() {
        searchForecastAdapter = SearchForecastAdapter(
            showIsSaveButton = false,
            onItemClicked = ::manageItemClick,
            onClickDeleteFromFavorite = ::deleteItemFromFavorite,

            )
        binding.searchListRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.searchListRv.adapter = searchForecastAdapter
    }

    private fun manageItemClick(entity: WeatherReportEntity) {
        val direction =
            FavoriteFragmentDirections.actionNavigationFavoriteToWeatherDetailsFragment(
                entity = entity
            )
        findNavController().navigate(direction)
    }

    private fun deleteItemFromFavorite(entity: WeatherReportEntity) {
        activity?.showAlertDialog(
            onContinueButton = {
                viewModel.deleteFromFavoriteById(entity.id)
                Utility.showMessage(
                    getString(R.string.has_been_deleted_from_favorite, entity.locationName),
                    binding.root
                )
            },
            warningMessage = getString(
                R.string.you_are_about_to_delete_from_favorite,
                entity.locationName
            )
        )
    }

    private fun handleEmptyState(isEmpty: Boolean) {
        binding.emptyState.isVisible = isEmpty
        binding.searchListRv.isVisible = !isEmpty
    }
}