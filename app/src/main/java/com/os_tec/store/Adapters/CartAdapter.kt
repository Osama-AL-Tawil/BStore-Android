package com.os_tec.store.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.os_tec.store.Model.CartDataModel
import com.os_tec.store.R

class CartAdapter (val activity: Activity, val data: ArrayList<CartDataModel>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val imageView=item.findViewById<ImageView>(R.id.imageView)!!
        val title=item.findViewById<TextView>(R.id.title)!!
        val brand=item.findViewById<TextView>(R.id.brand)!!
        val price=item.findViewById<TextView>(R.id.price)!!
        val count=item.findViewById<TextView>(R.id.count)!!
        val btnMin=item.findViewById<TextView>(R.id.btnMin)!!
        val btnPlus=item.findViewById<TextView>(R.id.btnPlus)!!
        val btnDelete=item.findViewById<MaterialButton >(R.id.btnDelete)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_checkout, parent, false)
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
        val count = data[position].count

        holder.imageView.setImageResource(image)
        holder.title.text = title
        holder.brand.text = brand
        holder.price.text = price
        holder.count.text = count.toString()


        holder.btnMin.setOnClickListener {
           val newCount=data[position].count -1
            if (newCount>0){
                holder.count.text=newCount.toString()
                data[position] = CartDataModel(title, brand, price, newCount, image)
            }

       }

        holder.btnPlus.setOnClickListener {
           val newCount=data[position].count +1
            holder.count.text=newCount.toString()
            data[position] = CartDataModel(title, brand, price, newCount, image)
       }

        holder.btnDelete.setOnClickListener {
            data.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }


    }
}