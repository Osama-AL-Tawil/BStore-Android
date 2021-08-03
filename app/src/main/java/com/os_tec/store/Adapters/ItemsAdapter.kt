package com.os_tec.store.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.os_tec.store.Activities.ItemDetails.ItemDetailsActivity
import com.os_tec.store.Model.ProductsDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcItemsBinding

class ItemsAdapter (val activity: Activity, val data: ArrayList<ProductsDataModel>) :
    RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding= RcItemsBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_items,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(activity).load(data[position].attachments[0].image).into(holder.binding.imageView)
        holder.binding.textTitle.text=data[position].title
        holder.binding.textPrice.text=data[position].price.toString()

       //send data to another view
        holder.binding.linearLayout.setOnClickListener {
            val intent=Intent(activity, ItemDetailsActivity::class.java)
            intent.putExtra("id",data[position].id)
            activity.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }
}