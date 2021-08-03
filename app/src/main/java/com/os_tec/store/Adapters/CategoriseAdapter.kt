package com.os_tec.store.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.os_tec.store.Model.CategoryObject
import com.os_tec.store.R
import com.os_tec.store.databinding.RcCategoriesBinding
import com.os_tec.store.databinding.RcCategoriesMBinding

class CategoriseAdapter(val activity: Activity, val data: ArrayList<CategoryObject>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val seeAllCategories:Int=1
    var view=0

    class MainCategories(item: View) : RecyclerView.ViewHolder(item) {
        val binding=RcCategoriesBinding.bind(item)
    }

    class SeeAllCategories(item: View) : RecyclerView.ViewHolder(item) {
        val binding=RcCategoriesMBinding.bind(item)
    }

    fun addView(value: Int) {
        view = value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder?

        if (viewType == seeAllCategories) {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.rc_categories_m, parent, false)
            viewHolder = SeeAllCategories(view)

        } else {
            val view = LayoutInflater.from(activity).inflate(R.layout.rc_categories, parent, false)
            viewHolder = MainCategories(view)


        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        var viewType:Int=0

        if (view==seeAllCategories){
            viewType=1
        }

        return viewType
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            seeAllCategories->{
                val viewHolder= holder as SeeAllCategories
                Glide.with(activity).load(data[position].image).into(viewHolder.binding.categoriesImage)
                viewHolder.binding.categoriesName.text=data[position].name
            }

            else->{
                val viewHolder= holder as MainCategories
                Glide.with(activity).load(data[position].image).into(viewHolder.binding.categoriesImage)
                viewHolder.binding.categoriesName.text=data[position].name
            }

        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}