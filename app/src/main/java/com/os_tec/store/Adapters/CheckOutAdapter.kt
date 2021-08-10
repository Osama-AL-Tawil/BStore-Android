package com.os_tec.store.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.os_tec.store.Model.CartModel
import com.os_tec.store.Model.MyOrderModel
import com.os_tec.store.Model.MyOrderResponseModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcOrderBinding

class CheckOutAdapter (val activity: Activity, val data: ArrayList<CartModel>) :
    RecyclerView.Adapter<CheckOutAdapter.MyViewHolder>() {

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding=RcOrderBinding.bind(item)
        val imageView=binding.orderImageView
        val title=binding.ordetTitle
        val brand=binding.orderBrand
        val price=binding.orderPrice
        val btnOrderAgain=binding.btnOrderAgain

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_order, parent, false)
        return MyViewHolder(view)
    }



    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(activity).load(data[position].attachments[0].image).into(holder.imageView)
        holder.title.text = data[position].title
        holder.brand.text = data[position].description
        holder.price.text = "$"+(data[position].price*data[position].quantity).toString()



    }
}