package com.example.weatherapp.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        //findviewsbyid

        fun onBind(data : PokoWeatherData){
            //set data
        }
    }
}