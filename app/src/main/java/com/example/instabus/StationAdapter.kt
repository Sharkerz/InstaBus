package com.example.instabus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StationAdapter(private val stationList: List<Station>, var clickListener: OnStationClickListener) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.station_item, parent, false)

        return StationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
//        var currentItem = stationList[position]
//
//        holder.name.text = currentItem.street_name
        holder.initialize(stationList.get(position), clickListener)
    }

    override fun getItemCount() = stationList.size

    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView)


        fun initialize(item: Station, action: OnStationClickListener) {
            name.text = item.street_name

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }
}

interface OnStationClickListener {
    fun onItemClick(item: Station, position: Int)
}
