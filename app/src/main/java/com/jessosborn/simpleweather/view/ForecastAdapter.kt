package com.jessosborn.simpleweather.view

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jessosborn.simpleweather.App.Companion.context
import com.jessosborn.simpleweather.R
import com.jessosborn.simpleweather.databinding.WeatherItemLayoutBinding
import com.jessosborn.simpleweather.model.repository.WeatherList
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.roundToInt

class ForecastAdapter(
    private val dataSet: List<WeatherList>,
) : RecyclerView.Adapter<ForecastAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            WeatherItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
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
                // clearColorFilter()
                Picasso.get()
                    .load("http://openweathermap.org/img/wn/${dataSet[position].weather[0].icon}@2x.png")
                    .into(ivWeatherIcon)
            }
            /*
            if (dataSet[position].main.temp == coldestIndex) {
                tvTemp.setTextColor(getColor(context, R.color.blueLight))
                tvTime.setTextColor(getColor(context, R.color.blueLight))
                ivWeatherIcon.setColorFilter(getColor(context, R.color.blueLight))
            } else if (dataSet[position] == hottestIndex) {
                tvTemp.setTextColor(getColor(context, R.color.orange))
                tvTime.setTextColor(getColor(context, R.color.orange))
                ivWeatherIcon.setColorFilter(getColor(context, R.color.orange))
            }
            */
        }
    }

    class CustomViewHolder(val binding: WeatherItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}