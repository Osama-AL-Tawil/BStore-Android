package com.os_tec.store.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.os_tec.store.Model.MyOrderModel
import com.os_tec.store.Model.MyOrderResponseModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcOrderBinding

class MyOrderAdapter (val activity: Activity, val data: ArrayList<MyOrderModel>) :
    RecyclerView.Adapter<MyOrderAdapter.MyViewHolder>() {

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
        var title=""
        for (i in data[position].product){
            title+=i.title
        }

        Glide.with(activity).load(data[position].product[0].attachments[0].image).into(holder.imageView)
        holder.title.text = title
        holder.brand.text = data[position].created_at
        holder.price.text = data[position].price.toString()



    }
}