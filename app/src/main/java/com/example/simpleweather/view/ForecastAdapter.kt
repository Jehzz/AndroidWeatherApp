package com.example.simpleweather.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweather.R
import com.example.simpleweather.model.repository.WeatherList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_item_layout.view.*

class ForecastAdapter(private val dataSet: List<WeatherList>) :
    RecyclerView.Adapter<ForecastAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_item_layout, parent, false)
        )

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(data: List<WeatherList>, position: Int) {
            //TODO: fix this, is called on each item, grossly inefficient
            var coldestIndex = 0
            var hottestIndex = 0
            for (i in data.indices) {
                if (data[i].main.temp > data[hottestIndex].main.temp) {
                    hottestIndex = i
                } else if (data[i].main.temp < data[coldestIndex].main.temp) {
                    coldestIndex = i
                }
            }

            itemView.apply {
                iv_weather_icon.clearColorFilter()
                tv_time.text = (data[position].dt_txt).substring(11, 16)
                tv_temp.text = data[position].main.temp + "°"

                Picasso.get()
                    .load("http://openweathermap.org/img/wn/${data[position].weather[0].icon}@2x.png")
                    .into(this.iv_weather_icon)

                if (position == coldestIndex) {
                    tv_temp.setTextColor(getColor(itemView.context, R.color.colorCool))
                    tv_time.setTextColor(getColor(itemView.context, R.color.colorCool))
                    iv_weather_icon.setColorFilter(getColor(itemView.context, R.color.colorCool))
                } else if (position == hottestIndex) {
                    tv_temp.setTextColor(getColor(itemView.context, R.color.colorWarm))
                    tv_time.setTextColor(getColor(itemView.context, R.color.colorWarm))
                    iv_weather_icon.setColorFilter(getColor(itemView.context, R.color.colorWarm))
                }
            }
        }
    }
}