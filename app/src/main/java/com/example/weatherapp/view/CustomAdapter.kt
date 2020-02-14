package com.example.weatherapp.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.PokoWeatherData

class CustomAdapter(val dataSet: PokoWeatherData) :
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

    override fun getItemCount(): Int = dataSet.list.size


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
        val ivWeatherIcon: ImageView = itemView.findViewById(R.id.iv_weather_icon)


        fun onBind(data: PokoWeatherData, position: Int) {
            //pass data to weather_item_layout views
            tvTime.text = data.list[position].dt_txt       //todo: trim time string to hours
            tvTemp.text = data.list[position].main.temp
            var iconString = data.list[position].weather[0].icon

            //todo: sorting function to find max and min temps
            //ivWeatherIcon  =
        }
    }
}