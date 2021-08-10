package com.os_tec.store.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.os_tec.store.Model.CardDataModel
import com.os_tec.store.Model.SliderModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcSliderBinding

class SliderAdapter(var context: Context, var data: ArrayList<SliderModel>) : PagerAdapter() {

    lateinit var inflater:LayoutInflater



    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view:View=LayoutInflater.from(context).inflate(R.layout.rc_slider,container,false)
        val binding=RcSliderBinding.bind(view)

        Glide.with(context).load(data[position].image).into(binding.image)
        binding.title.text = data[position].title
        binding.description.text = data[position].description

        container.addView(binding.root)


        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view == `object`
    }
}