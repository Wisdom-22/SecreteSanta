package com.example.andriod.secretesanta

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyHomeOutdoorsAdapter(val context: Context, val userList: List<HomeOutdoorsListItem>): RecyclerView.Adapter<MyHomeOutdoorsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var price: TextView
        lateinit var description: TextView
        lateinit var image: ImageView

        init{
            price = itemView.findViewById(R.id.price)
            description = itemView.findViewById(R.id.description)
            image = itemView.findViewById(R.id.image_product)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = "$" + userList[position].prices.toString()
        holder.description.text = userList[position].description.toString()
        //Picasso.get().load(userList[position].url).into(holder.image)
        Glide.with(context).load(userList[position].url).into(holder.image)
    }


    override fun getItemCount(): Int {
        return userList.size
    }
}