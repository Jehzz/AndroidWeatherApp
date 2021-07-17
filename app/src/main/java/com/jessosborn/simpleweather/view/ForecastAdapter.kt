package com.jessosborn.simpleweather.view

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.jessosborn.simpleweather.R
import com.jessosborn.simpleweather.databinding.WeatherItemLayoutBinding
import com.jessosborn.simpleweather.model.repository.WeatherList
import com.squareup.picasso.Picasso
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class ForecastAdapter(
    private val context: Context,
    private val dataSet: List<WeatherList>,
) : RecyclerView.Adapter<ForecastAdapter.CustomViewHolder>() {

    var coldestIndex = 0
    var hottestIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        findExtremeTempIndices()
        return CustomViewHolder(
            WeatherItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun findExtremeTempIndices() {
        for (i in dataSet.indices) {
            if (dataSet[i].main.temp > dataSet[hottestIndex].main.temp) {
                hottestIndex = i
            } else if (dataSet[i].main.temp < dataSet[coldestIndex].main.temp) {
                coldestIndex = i
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.apply {
            tvTemp.text = context.getString(
                R.string.degrees, dataSet[position].main.temp.roundToInt()
            )
            tvTime.text = DateFormat.format(
                if (DateFormat.is24HourFormat(context)) "H:00" else "h:00",
                Calendar.getInstance(Locale.ENGLISH).apply {
                    timeInMillis = dataSet[position].dt.toLong() * 1000L
                }
            )
            ivWeatherIcon.apply {
                Picasso.get()
                    .load("https://openweathermap.org/img/wn/${dataSet[position].weather[0].icon}@2x.png")
                    .into(ivWeatherIcon)
            }

            if (position == coldestIndex) {
                tvTemp.setTextColor(getColor(context, R.color.blueLight))
                tvTime.setTextColor(getColor(context, R.color.blueLight))
                ivWeatherIcon.setColorFilter(getColor(context, R.color.blueLight))
            } else if (position == hottestIndex) {
                tvTemp.setTextColor(getColor(context, R.color.orange))
                tvTime.setTextColor(getColor(context, R.color.orange))
                ivWeatherIcon.setColorFilter(getColor(context, R.color.orange))
            }
        }
    }

    class CustomViewHolder(val binding: WeatherItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
