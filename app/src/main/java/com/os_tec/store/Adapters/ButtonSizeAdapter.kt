package com.os_tec.store.Adapters

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.store.Activities.ItemDetails.ItemDetailsActivity
import com.os_tec.store.Model.SizeDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcButtonBinding

class ButtonSizeAdapter (val activity: Activity, val data: ArrayList<SizeDataModel>) :
    RecyclerView.Adapter<ButtonSizeAdapter.MyViewHolder>() {
    var checkedButtonIndex =1

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding=RcButtonBinding.bind(itemView)
        val btnSize= binding.btnSize
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = RcButtonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(inflater.root)
    }



    override fun getItemCount(): Int {
        return data.size
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.btnSize.setText(data[position].size)
        changeColor(holder,data[position].checked,data[position].size)
        holder.btnSize.setOnClickListener {
            checkedButton(position)
        }


    }
    fun checkedButton(position: Int){

//        val mainArray = (activity as ItemDetailsActivity).sizeArray
         if (data[position].checked){
             Toast.makeText(activity,"Selected Size ${data[position].size}",Toast.LENGTH_LONG).show()

         }else{
             val oldSize=data[checkedButtonIndex].size
             //mainArray.remove(data[checkedButtonIndex])
             //mainArray.add(checkedButtonIndex, SizeDataModel(oldSize,false))

             val newSize=data[position].size
             //mainArray.remove(data[position])
            // mainArray.add(position, SizeDataModel(newSize,true))
             checkedButtonIndex=position

            notifyItemRemoved(position)
                 notifyDataSetChanged()

             }

         }



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun changeColor(holder: MyViewHolder, checked:Boolean, size:String){
        if (checked) {
            holder.btnSize.setBackgroundColor(ContextCompat.getColor(activity,R.color.blue1))
            holder.btnSize.setTextColor(ContextCompat.getColor(activity,R.color.white))
        }else{
            if (size=="XXL"){
                holder.btnSize.setBackgroundColor(ContextCompat.getColor(activity,R.color.white))
                holder.btnSize.setTextColor(ContextCompat.getColor(activity,R.color.black))
                holder.btnSize.stateListAnimator=null
            }else{
                holder.btnSize.setBackgroundColor(ContextCompat.getColor(activity,R.color.gray3))
                holder.btnSize.setTextColor(ContextCompat.getColor(activity,R.color.black))
            }

        }
    }
}