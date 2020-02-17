package com.example.weatherapp.view


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.PokoForecastWeatherData
import com.squareup.picasso.Picasso

class ForecastAdapter(val dataSet: PokoForecastWeatherData) :
    RecyclerView.Adapter<ForecastAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.weather_item_layout,
                    parent,
                    false
                )
        )

    /**
     * Limits the returned data to only the end of tomorrow
     * @author: Jess Osborn
     */
    override fun getItemCount(): Int = 16


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)
    }

    /**
     * Binds data from the dataset to weather_item_layout views. Includes logic to color code
     * daily high and low temperatures. +8 to position makes the first item the weather in 24hours from now
     * @author: Jess Osborn
     */
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTime: TextView = itemView.findViewById(R.id.tv_time)
        var tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
        var ivWeatherIcon: ImageView = itemView.findViewById(R.id.iv_weather_icon)

        fun onBind(data: PokoForecastWeatherData, position: Int) {
            //pass data to weather_item_layout views
            tvTime.text = (data.list[position + 8].dt_txt).substring(11, 16)
            tvTemp.text =
                data.list[position + 8].main.temp + "°"   //substring method here risks index out of bounds. Use delimiter instead?
            var iconString = data.list[position + 8].weather[0].icon

            //Find high and low temps. This is called on each item, so grossly inefficient
            var lowIndex = 0
            var highIndex = 0
            for (i in 1..7) {
                if (data.list[i].main.temp > data.list[highIndex].main.temp) {
                    highIndex = i
                }
                if (data.list[i].main.temp < data.list[lowIndex].main.temp) {
                    lowIndex = i
                }
            }

            Picasso.get().load("http://openweathermap.org/img/wn/$iconString@2x.png")
                .into(ivWeatherIcon)
            if (position == lowIndex) {
                tvTemp.setTextColor(Color.parseColor("#03a9f4"))
                tvTime.setTextColor(Color.parseColor("#03a9f4"))
                ivWeatherIcon.setColorFilter(Color.parseColor("#03a9f4"))
            } else if (position == highIndex) {
                tvTemp.setTextColor(Color.parseColor("#ff9800"))
                tvTime.setTextColor(Color.parseColor("#ff9800"))
                ivWeatherIcon.setColorFilter(Color.parseColor("#ff9800"))
            }
        }
    }
}