package com.os_tec.store.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Model.AddressDataModel
import com.os_tec.store.R

class AddressAdapter (val activity: Activity, val data: ArrayList<AddressDataModel>) :
    RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {
    var itemPositionChecked=0

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val street=item.findViewById<TextView>(R.id.street)!!
        val roadNo=item.findViewById<TextView>(R.id.roadNo)!!
        val houseNo=item.findViewById<TextView>(R.id.houseNo)!!
        val radioButton=item.findViewById<RadioButton>(R.id.radioButton)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rc_address, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val street=data[position].street
        val roadNo=data[position].roadNo
        val houseNo=data[position].houseNo
        holder.street.text=street
        holder.roadNo.text=roadNo
        holder.houseNo.text=houseNo

        holder.radioButton.isChecked = data[position].radioButton=="checked"

        holder.radioButton.setOnClickListener {
                holder.radioButton.isChecked=true
            //get MainFragment
            val addressFragment= (activity as Navigation2Activity).addressFragment
                 //Edit main array data
               //edit and remove checked form oldPosition
                val oldData=addressFragment.addressArray[itemPositionChecked]
                addressFragment.addressArray.removeAt(itemPositionChecked)
                addressFragment.addressArray.add(itemPositionChecked,
                    AddressDataModel(oldData.street,oldData.roadNo,oldData.houseNo,"unChecked")
                )
                notifyItemRemoved(itemPositionChecked)

                //edit and addChecked in newPosition
                addressFragment.addressArray.removeAt(position)
                addressFragment.addressArray.add(position,
                    AddressDataModel(street,roadNo,houseNo,"checked")
                )

                //add CheckedPosition
                itemPositionChecked=position

                notifyItemChanged(position)
                notifyDataSetChanged()
            }




    }

    override fun getItemCount(): Int {
        return data.size
    }
}