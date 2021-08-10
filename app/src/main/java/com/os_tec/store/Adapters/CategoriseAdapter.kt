package com.os_tec.store.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.os_tec.store.Activities.ShowItems.ShowItemsActivity
import com.os_tec.store.Model.CategoryModel
import com.os_tec.store.R
import com.os_tec.store.databinding.RcCategoriesBinding
import com.os_tec.store.databinding.RcCategoriesMBinding
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class CategoriseAdapter(val activity: Activity, val data: ArrayList<CategoryModel>) :
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

                viewHolder.binding.cardView.setOnClickListener {
                    val i=Intent(activity, ShowItemsActivity::class.java)
                    i.putExtra("nv","ShowCategoryItems")
                    i.putExtra("category_id",data[position].id)
                    i.putExtra("title",data[position].name)

                    activity.startActivity(i)
                }
            }

            else->{
                val viewHolder= holder as MainCategories
                Glide.with(activity).load(data[position].image).into(viewHolder.binding.categoriesImage)
                viewHolder.binding.categoriesName.text=data[position].name

                viewHolder.binding.cardView.setOnClickListener {
                    val i=Intent(activity, ShowItemsActivity::class.java)
                    i.putExtra("nv","ShowCategoryItems")
                    i.putExtra("category_id",data[position].id)
                    i.putExtra("title",data[position].name)
                    activity.startActivity(i)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}