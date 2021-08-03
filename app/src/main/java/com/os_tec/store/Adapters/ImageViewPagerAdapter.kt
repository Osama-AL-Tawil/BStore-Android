package com.os_tec.store.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.os_tec.store.Model.AttachmentsData
import com.os_tec.store.R
import com.os_tec.store.databinding.RcImageViewpagerBinding

class ImageViewPagerAdapter(var context: Context, var data: ArrayList<AttachmentsData>) : PagerAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view=RcImageViewpagerBinding.bind(LayoutInflater.from(context).inflate(R.layout.rc_image_viewpager,container,false))

        val image=view.sliderImage

        //val id=data[position].id
        Glide.with(context).load(data[position].image).into(image)


        container.addView(view.root)


        return view.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view == `object`
    }
}