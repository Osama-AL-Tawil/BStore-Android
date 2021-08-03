package com.os_tec.store.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.os_tec.store.Model.CardDataModel
import com.os_tec.store.R

class ViewPagerAdapter(var context: Context, var data: ArrayList<CardDataModel>) : PagerAdapter() {

    lateinit var inflater:LayoutInflater



    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
        val view:View=inflater.inflate(R.layout.card_item,container,false)

        val cardNo:TextView=view.findViewById(R.id.cardNo)
        val cardDate:TextView=view.findViewById(R.id.cardDate)
        val cardName:TextView=view.findViewById(R.id.cardUserName)

        cardNo.text=data[position].cardNo
        cardDate.text=data[position].cardDate
        cardName.text=data[position].cardName

        //image.setImageResource(images[position])
        container.addView(view)


        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view == `object`
    }
}