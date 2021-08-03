package com.os_tec.store.Adapters

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.store.Activities.MainNavigation
import com.os_tec.store.databinding.RcNavigationItemBinding

class NavigationAdapter(val activity: Activity, val data: ArrayList<String>) :
RecyclerView.Adapter<NavigationAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding= RcNavigationItemBinding.bind(itemView)
        val btnNav= binding.btnNav
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RcNavigationItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding.root)
    }



    override fun getItemCount(): Int {
        return data.size
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.btnNav.text=data[position]

        val nvActivity=(activity as MainNavigation)

        holder.btnNav.setOnClickListener {
        nvActivity.navigateToFragment(position)
        }


    }

}