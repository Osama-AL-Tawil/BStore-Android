package com.os_tec.store.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.store.Model.CartDataModel
import com.os_tec.store.R

class MyOrderAdapter (val activity: Activity, val data: ArrayList<CartDataModel>) :
    RecyclerView.Adapter<MyOrderAdapter.MyViewHolder>() {

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val imageView=item.findViewById<ImageView>(R.id.orderImageView)!!
        val title=item.findViewById<TextView>(R.id.ordetTitle)!!
        val brand=item.findViewById<TextView>(R.id.orderBrand)!!
        val price=item.findViewById<TextView>(R.id.orderPrice)!!
        val btnOrderAgain=item.findViewById<TextView>(R.id.btnOrderAgain)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_order, parent, false)
        return MyViewHolder(view)
    }



    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = data[position].image
        val title = data[position].title
        val brand = data[position].brand
        val price = data[position].price
        //val count = data[position].count

        holder.imageView.setImageResource(image)
        holder.title.text = title
        holder.brand.text = brand
        holder.price.text = price



    }
}