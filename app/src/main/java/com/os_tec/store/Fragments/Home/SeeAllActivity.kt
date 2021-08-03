package com.os_tec.store.Fragments.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Adapters.CategoriseAdapter
import com.os_tec.store.Adapters.ItemsAdapter
import com.os_tec.store.Classes.RecyclerViewDecoration
import com.os_tec.store.Model.CategoryObject
import com.os_tec.store.Model.ProductsDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivitySeeAllBinding

class SeeAllActivity : AppCompatActivity() {
    lateinit var binding:ActivitySeeAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySeeAllBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)


        //get data
        val nv=intent.getStringExtra("nv")
        val data=intent.getSerializableExtra("data")

        val decoration= RecyclerViewDecoration(
            resources.getDimensionPixelSize(R.dimen.marginHorizontal),
            resources.getDimensionPixelSize(R.dimen.marginHorizontal),
            resources.getDimensionPixelSize(R.dimen.marginTop),
            resources.getDimensionPixelSize(R.dimen.marginBottom))


        when(nv){
            "Categories"->{
                setTitle(resources.getString(R.string.Categories))
                Log.e("dataData",data.toString())
                binding.rcSeeAll.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                val adapterItems= CategoriseAdapter(this,data as ArrayList<CategoryObject>)
                adapterItems.addView(1)
                binding.rcSeeAll.adapter=adapterItems

            }
            "Featured"->{
                setTitle(resources.getString(R.string.Featured))
                Log.e("dataData",data.toString())

                val adapterItems= ItemsAdapter(this,data as ArrayList<ProductsDataModel>)
                binding.rcSeeAll.layoutManager = GridLayoutManager(this,2)
                binding.rcSeeAll.addItemDecoration(decoration)
                binding.rcSeeAll.adapter=adapterItems

            }
            "BestSell"->{
                setTitle(resources.getString(R.string.bestSell))
                Log.e("dataData",data.toString())

                val adapterItems= ItemsAdapter(this,data as ArrayList<ProductsDataModel>)
                binding.rcSeeAll.layoutManager = GridLayoutManager(this,2)
                binding.rcSeeAll.addItemDecoration(decoration)
                binding.rcSeeAll.adapter=adapterItems
            }
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setTitle(text:String){
        binding.title.text=text

    }

}