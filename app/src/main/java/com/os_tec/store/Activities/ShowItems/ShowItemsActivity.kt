package com.os_tec.store.Activities.ShowItems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Adapters.CategoriseAdapter
import com.os_tec.store.Adapters.ItemsAdapter
import com.os_tec.store.Classes.CustomAlertDialog
import com.os_tec.store.Classes.RecyclerViewDecoration
import com.os_tec.store.Model.CategoryModel
import com.os_tec.store.Model.ProductsModel
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivitySeeAllBinding
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class ShowItemsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySeeAllBinding
    lateinit var decoration:RecyclerViewDecoration
    lateinit var viewModel: ShowItemViewModel
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySeeAllBinding.inflate(LayoutInflater.from(this))
        val alertDialog=CustomAlertDialog(this)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)


        //get data
        val nv=intent.getStringExtra("nv")
        val categoryId=intent.getIntExtra("category_id",0)
        val title=intent.getStringExtra("title").toString()
        val data=intent.getSerializableExtra("data")

        viewModel= ShowItemViewModel()

         decoration= RecyclerViewDecoration(
            resources.getDimensionPixelSize(R.dimen.marginHorizontal),
            resources.getDimensionPixelSize(R.dimen.marginHorizontal),
            resources.getDimensionPixelSize(R.dimen.marginTop),
            resources.getDimensionPixelSize(R.dimen.marginBottom))


        when(nv){
            "Categories"->{
                setTitle(resources.getString(R.string.Categories))
                Log.e("dataData",data.toString())
                binding.rcSeeAll.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                val adapterItems= CategoriseAdapter(this,data as ArrayList<CategoryModel>)
                adapterItems.addView(1)
                binding.rcSeeAll.adapter=adapterItems

            }

            "Featured"->{
                setTitle(resources.getString(R.string.Featured))
                Log.e("dataData",data.toString())
                setGridAdapter(data as ArrayList<ProductsModel>)

            }

            "BestSell"->{
                setTitle(resources.getString(R.string.bestSell))
                Log.e("dataData",data.toString())
                setGridAdapter(data as ArrayList<ProductsModel>)

            }

            "ShowCategoryItems"->{
                setTitle(title)
                viewModel.getCategoryItem(this,categoryId).observe({lifecycle},{
                    setGridAdapter(it)
                }) //get data

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

    private fun setGridAdapter(data: ArrayList<ProductsModel>) {
        binding.rcSeeAll.layoutManager = GridLayoutManager(this, 2)
        val adapterItems = ItemsAdapter(this, data )
        binding.rcSeeAll.addItemDecoration(decoration)
        binding.rcSeeAll.adapter = adapterItems
    }


    private fun setTitle(text:String){
        binding.title.text=text

    }


}