package com.os_tec.store.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.os_tec.store.Activities.ItemDetails.ItemDetailsActivity
import com.os_tec.store.Fragments.Favorite.FavoriteViewModel
import com.os_tec.store.Model.ItemsDataModel
import com.os_tec.store.Model.ProductsDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcFavoriteBinding

class FavoriteAdapter (val activity: Activity, val data: ArrayList<ProductsDataModel>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

     lateinit var viewModel:FavoriteViewModel

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding=RcFavoriteBinding.bind(item)

        val imageView=binding.image
        val title=binding.title
        val price=binding.price
        val description=binding.description
        val btnFavorite=binding.btnFavorite
        val click=binding.cardView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_favorite, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(activity).load(data[position].attachments[0].image).into(holder.imageView)
        holder.title.text=data[position].title
        holder.price.text=data[position].price.toString()
        holder.description.text=data[position].description

        holder.btnFavorite.setOnClickListener {
            viewModel = FavoriteViewModel()
            //remove data from db
            viewModel.addAndRemoveFavorite(data[position].id, 0)
            //remove data form array
            data.remove(data[position])
            notifyItemRemoved(position)
            notifyDataSetChanged()

        }

        holder.click.setOnClickListener {
            val intent=Intent(activity, ItemDetailsActivity::class.java)
            intent.putExtra("id",data[position].id)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}