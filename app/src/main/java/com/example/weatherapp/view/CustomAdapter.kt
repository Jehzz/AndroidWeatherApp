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

class CustomAdapter(val dataSet: PokoForecastWeatherData) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.weather_item_layout,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)

    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvTime: TextView = itemView.findViewById(R.id.tv_time)
        var tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
        var ivWeatherIcon: ImageView = itemView.findViewById(R.id.iv_weather_icon)

        fun onBind(data: PokoForecastWeatherData, position: Int) {
            //pass data to weather_item_layout views
            tvTime.text = (data.list[position].dt_txt).substring(11, 16)
            tvTemp.text = data.list[position].main.temp + "°"
            var iconString = data.list[position].weather[0].icon


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
            //assign color and icon based on high or low temp
            if (position == lowIndex) {
                tvTemp.setTextColor(Color.parseColor("#03a9f4"))
                tvTime.setTextColor(Color.parseColor("#03a9f4"))
            } else if (position == highIndex) {
                tvTemp.setTextColor(Color.parseColor("#ff9800"))
                tvTime.setTextColor(Color.parseColor("#ff9800"))
            }
            Picasso.get().load("http://openweathermap.org/img/wn/$iconString@2x.png")
                .into(ivWeatherIcon)
        }
    }
}