package com.example.weatherapp.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.PokoWeatherData

class CustomAdapter(val dataSet : List<PokoWeatherData>) :
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

    override fun getItemCount(): Int = dataSet.size


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
        val ivWeatherIcon: ImageView = itemView.findViewById(R.id.iv_weather_icon)

        fun onBind(data : PokoWeatherData){
            tvTemp.text = data.list[0].main.temp
        }
    }
}