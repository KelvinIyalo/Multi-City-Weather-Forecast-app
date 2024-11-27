package com.kelvin.weatherappkelvin.data.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.data.models.weather.WeatherForecastDays
import com.kelvin.weatherappkelvin.databinding.ForecastListLayoutBinding

class ForecastAdapter(
) : ListAdapter<WeatherForecastDays, ForecastAdapter.WeatherForecastItemVH>(object :
    DiffUtil.ItemCallback<WeatherForecastDays>() {

    override fun areItemsTheSame(
        oldItem: WeatherForecastDays,
        newItem: WeatherForecastDays
    ): Boolean {
        return oldItem.avghumidity == newItem.avghumidity
    }

    override fun areContentsTheSame(
        oldItem: WeatherForecastDays,
        newItem: WeatherForecastDays
    ): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastItemVH {
        //inflate the view to be used by the payment option view holder
        val binding =
            ForecastListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherForecastItemVH(binding)

    }

    override fun getItemCount(): Int = currentList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherForecastItemVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class WeatherForecastItemVH(
        private val binding: ForecastListLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(forecast: WeatherForecastDays) {
            with(binding) {
                forecastDate.text = Utility.formatDate(forecast.forecastDay)
                forecastImage.setImageResource(Utility.showWeatherImage(forecast.condition))
                forecastHumidity.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    Utility.updateImageByPercentage(forecast.avghumidity.toFloat()), // DrawableStart
                    0,                            // DrawableTop
                    0,                            // DrawableEnd
                    0                             // DrawableBottom
                )
                forecastHumidity.text = buildString {
                    append(forecast.avghumidity)
                    append("%")
                }

                forecastTemp.text = buildString {
                    append(forecast.avgtemp_c)
                    append("°C")
                }
            }
        }

    }
}