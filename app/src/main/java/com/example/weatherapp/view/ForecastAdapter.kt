package com.example.weatherapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.App.Companion.context
import com.example.weatherapp.R
import com.example.weatherapp.model.repository.weatherlist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_item_layout.view.*

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

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(data: List<weatherlist>, position: Int) {
            itemView.iv_weather_icon.clearColorFilter()
            itemView.tv_time.text = (data[position].dt_txt).substring(11, 16)
            itemView.tv_temp.text = data[position].main.temp + "°"

            //TODO: fix this
            //Find high and low temps. This is called on each item, so grossly inefficient
            var coldestIndex = 0
            var hottestIndex = 0
            for (i in data.indices) {
                if (data[i].main.temp > data[hottestIndex].main.temp) {
                    hottestIndex = i
                } else if (data[i].main.temp < data[coldestIndex].main.temp) {
                    coldestIndex = i
                }
            }

            Picasso.get()
                .load("http://openweathermap.org/img/wn/${data[position].weather[0].icon}@2x.png")
                .resize(400, 400)
                .into(itemView.iv_weather_icon)
            //assign color based on high or low temp
            if (position == coldestIndex) {
                itemView.tv_temp.setTextColor(context.getColor(R.color.colorCool))
                itemView.tv_time.setTextColor(context.getColor(R.color.colorCool))
                itemView.iv_weather_icon.setColorFilter(context.getColor(R.color.colorCool))
            } else if (position == hottestIndex) {
                itemView.tv_temp.setTextColor(context.getColor(R.color.colorWarm))
                itemView.tv_time.setTextColor(context.getColor(R.color.colorWarm))
                itemView.iv_weather_icon.setColorFilter(context.getColor(R.color.colorWarm))
            }
        }
    }
}