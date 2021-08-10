package com.os_tec.store.Adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.os_tec.store.Activities.ItemDetails.DetailsViewModel
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Model.CartModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcCheckoutBinding


class CartAdapter (val activity: Activity, val data: ArrayList<CartModel>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    lateinit var viewModel:DetailsViewModel
    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding=RcCheckoutBinding.bind(item)
        val imageView=binding.imageView
        val title=binding.title
        val price=binding.price
        val count=binding.Count
        val btnMin=binding.btnMin
        val btnPlus=binding.btnPlus
        val btnDelete=binding.btnDelete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_checkout, parent, false)
        return MyViewHolder(view)
    }



    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        viewModel=DetailsViewModel()
        val product=data[position].product
        var quantity=data[position].quantity

        Glide.with(activity).load(data[position].attachments[0].image).into(holder.imageView)
        holder.title.text = data[position].title
        holder.price.text = "$"+data[position].price.toString()
        holder.count.text = quantity.toString()


        holder.btnMin.setOnClickListener {
                    if (quantity>1){
                        val newQuantity=quantity-1
                        holder.count.text=newQuantity.toString()
                        quantity=newQuantity

                    }else{
                        Toast.makeText(activity, activity.resources.getString(R.string.lowestQuantity), Toast.LENGTH_SHORT).show()
                    }

       }



        holder.btnPlus.setOnClickListener {
            Log.e("CartAdapter","Stock:${data[position].product.stock}")
            Log.e("CartAdapter","Quantity:${data[position].quantity}")

            val newQuantity=data[position].quantity+1
            data[position].quantity=newQuantity
            holder.count.text=newQuantity.toString()

            ApiRepository(activity).updateCart("CartAdapter",data[position].cart_id,newQuantity)

       }



        holder.btnDelete.setOnClickListener {
            ApiRepository(activity).deleteFromCart("CartAdapter",data[position].cart_id)
            data.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }


    }

}