package com.example.instabus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instabus.DBHelper.DBHelper
import com.example.instabus.Utils.Utils
import androidx.core.app.ActivityCompat
import androidx.core.view.get

class PictureAdapter(private val pictureList: MutableList<Picture>) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.picture_item, parent, false)

        return PictureViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        var currentItem = pictureList[position]
        var image = Utils.getImage(currentItem.image)
        var current_date = currentItem.date

        holder.name.setText(currentItem.title)
        holder.picture.setImageBitmap(image)
        holder.itemView.setTag(currentItem.id)
        holder.date.setText("Ajouté le : $current_date")
    }

    override fun getItemCount() = pictureList.size

    class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.title)
        val picture: ImageView = itemView.findViewById(R.id.picture)
        val date: TextView = itemView.findViewById(R.id.date)

    }

    //Delete Item
    fun deleteItem(pos:Int){
        pictureList.removeAt(pos)
        notifyDataSetChanged()
    }
}
