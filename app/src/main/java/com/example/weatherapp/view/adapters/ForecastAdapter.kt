package com.example.weatherapp.view.adapters


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.data.weatherlist
import com.squareup.picasso.Picasso

/**
 * Class specific for translating today's weather data into a recyclerview
 * @author: Jess Osborn
 */
class ForecastAdapter(private val dataSet: List<weatherlist>) :
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

    /**z
     * Limits the number of returned items from the dataset to just the next 24H of data
     * @author: Jess Osborn
     */
    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)
    }

    /**
     * Binds data from the dataset to weather_item_layout views. Includes logic to color code
     * daily high and low temperatures
     * @author: Jess Osborn
     */
    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvTime: TextView = itemView.findViewById(R.id.tv_time)
        var tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
        var ivWeatherIcon: ImageView = itemView.findViewById(R.id.iv_weather_icon)

        fun onBind(data: List<weatherlist>, position: Int) {
            //pass data to weather_item_layout views
            ivWeatherIcon.clearColorFilter()
            tvTime.text = (data[position].dt_txt).substring(11, 16)
            tvTemp.text = data[position].main.temp + "°"
            var iconString = data[position].weather[0].icon


            //Find high and low temps. This is called on each item, so grossly inefficient
            var lowIndex = 0
            var highIndex = 0
            for (i in 1..7) {
                if (data[i].main.temp > data[highIndex].main.temp) {
                    highIndex = i
                }
                if (data[i].main.temp < data[lowIndex].main.temp) {
                    lowIndex = i
                }
            }
            //TODO custom icons
            Picasso.get().load("http://openweathermap.org/img/wn/$iconString@2x.png")
                .resize(400, 400)
                .centerCrop()
                .into(ivWeatherIcon)
            //assign color based on high or low temp
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