package com.os_tec.store.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Model.SettingDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcSettingBinding

class SettingAdapter(val activity: Activity, val data: ArrayList<SettingDataModel>) :
    RecyclerView.Adapter<SettingAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RcSettingBinding.bind(itemView)
        val title = binding.title
        val icon = binding.icon
        val click=binding.click

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_setting, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val activity=(activity as Navigation3Activity)
        holder.title.text=data[position].title
        holder.icon.setImageResource(data[position].image)

        holder.click.setOnClickListener {
            when(data[position].navigation){
                "resetPassword"->{
                    activity.startFragment(activity.resetPasswordFragment)

                }
            }
        }



    }

    override fun getItemCount(): Int {
        return data.size
    }
}