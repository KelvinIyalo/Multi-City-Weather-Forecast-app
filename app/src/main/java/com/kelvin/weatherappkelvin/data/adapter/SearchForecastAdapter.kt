package com.kelvin.weatherappkelvin.data.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.data.models.weather.WeatherReportEntity
import com.kelvin.weatherappkelvin.databinding.SearchItemsLayoutBinding

class SearchForecastAdapter(
    private val showIsSaveButton: Boolean = true,
    private val onItemClicked: (itemAtPosition: WeatherReportEntity) -> Unit,
    private val onClickSaveToFavorite: (itemAtPosition: WeatherReportEntity) -> Unit = {},
    private val onClickDeleteFromFavorite: (itemAtPosition: WeatherReportEntity) -> Unit = {}
) : ListAdapter<WeatherReportEntity, SearchForecastAdapter.WeatherForecastItemVH>(object :
    DiffUtil.ItemCallback<WeatherReportEntity>() {

    override fun areItemsTheSame(
        oldItem: WeatherReportEntity,
        newItem: WeatherReportEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: WeatherReportEntity,
        newItem: WeatherReportEntity
    ): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastItemVH {
        //inflate the view to be used by the payment option view holder
        val binding =
            SearchItemsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherForecastItemVH(binding)

    }

    override fun getItemCount(): Int = currentList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherForecastItemVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class WeatherForecastItemVH(
        private val binding: SearchItemsLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(forecast: WeatherReportEntity) {
            with(binding) {

                saveBtn.apply {
                    visibility = if (showIsSaveButton) View.VISIBLE else View.GONE
                    setOnClickListener {
                        onClickSaveToFavorite.invoke(forecast)
                        val message = buildString {
                            append(forecast.locationName)
                            append(" ")
                            append(context.getString(R.string.has_been_added_to_favorite))
                        }
                        Utility.showMessage(message, binding.root)
                    }
                }
                deleteItem.apply {
                    visibility = if (!showIsSaveButton) View.VISIBLE else View.GONE
                    setOnClickListener { onClickDeleteFromFavorite.invoke(forecast) }
                }
                rootCard.setOnClickListener {
                    onItemClicked.invoke(forecast)
                }
                dateTime.text = Utility.formatTimeAndDate(forecast.locationTime)
                forecastImage.setImageResource(Utility.showWeatherImage(forecast.locationCondition))
                temp.text = buildString {
                    append(forecast.temp_c)
                    append("°")
                }
                place.text = forecast.locationName
                fullRegion.text = buildString {
                    append(forecast.locationName)
                    append(", ")
                    append(forecast.locationCountry)
                }
            }
        }

    }
}