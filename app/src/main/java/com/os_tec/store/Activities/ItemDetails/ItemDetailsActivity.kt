package com.os_tec.store.Activities.ItemDetails

import android.content.Intent
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Adapters.ImageViewPagerAdapter
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Classes.CustomAlertDialog
import com.os_tec.store.Classes.SeeMoreTextView
import com.os_tec.store.Fragments.Favorite.FavoriteViewModel
import com.os_tec.store.Model.DetailsResponseModel
import com.os_tec.store.Model.ProductsDataModel
import com.os_tec.store.Model.SizeDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivityItemDetailsBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ItemDetailsActivity : AppCompatActivity() {
    private val logKey = "DetailsActivity.OS"
    private lateinit var dialog: CustomAlertDialog
    lateinit var binding: ActivityItemDetailsBinding
    lateinit var viewModel: DetailsViewModel
    lateinit var favoriteViewModel: FavoriteViewModel
    var count=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // Set a Toolbar to replace the ActionBar.
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        //Product id
        val id=intent.getIntExtra("id",0)
        //dialog
        dialog= CustomAlertDialog(this)

        //initialized ViewModel
        viewModel=DetailsViewModel() //DetailsViewModel
        favoriteViewModel= FavoriteViewModel()//FavoriteViewModel



        //getAnd Set Data------------------------------------
        observeAndSetData(id)


        binding.btnBuyNow.setOnClickListener {
         val i=Intent(this, Navigation2Activity::class.java)
            i.putExtra("nv","cart")
            startActivity(i)
        }


        binding.btnAddCart.setOnClickListener {
            Toast.makeText(this,"The product Add to Cart",Toast.LENGTH_SHORT).show()
            finish()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }



   // select quantity ------------------------------------------------------------------------------
   private fun minCount(){
        if (count!=1){
            val newCount=count-1
            binding.count.text=newCount.toString()
            count=newCount

        }else{
            Toast.makeText(this, resources.getString(R.string.lowestQuantity), Toast.LENGTH_SHORT).show()
        }


    }

    private fun plusCount(stock: Int) {
        if (count <= stock) {
            val newCount = count + 1
            binding.count.text = newCount.toString()
            count = newCount

        } else {
            Toast.makeText(this, resources.getString(R.string.stockOut), Toast.LENGTH_SHORT)
                .show()
        }


    }



    //Bind data in view
    private fun bindData(data: ProductsDataModel) {
        //bind data in view
        binding.textTitle.text = data.title
        binding.textPrice.text = data.price.toString()

        //ImageSlider
        val viewPager = binding.imageSlider
        val viewPagerAdapter =
            ImageViewPagerAdapter(this@ItemDetailsActivity, data.attachments)
        viewPager.adapter = viewPagerAdapter

        //set viewPager indicator ->Dots
        binding.indicator.viewPager = viewPager


        //discount TextView
        val discount = binding.textDiscount
        discount.text = "$00.0"
        discount.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG


        //set max and min lines in Text View
        val descriptionTV = binding.textDescription
        descriptionTV.text = data.description
        val seeMore = SeeMoreTextView(this@ItemDetailsActivity)
        val color = ContextCompat.getColor(this@ItemDetailsActivity, R.color.blue1).toString()

        seeMore.setText(
            descriptionTV,
            data.description,
            color,
            "BOLD",
            resources.getString(R.string.More)
        )

        //textView On click
        descriptionTV.setOnClickListener {
            seeMore.setText(
                descriptionTV,
                data.description,
                color,
                "BOLD",
                resources.getString(R.string.More)
            )
        }


        //select quantity
        binding.btnMin.setOnClickListener {
            minCount()
        }

        binding.btnPlus.setOnClickListener {
            plusCount(data.stock)
        }

        //add in favorite
        binding.btnFavorite.setOnClickListener {
            favoriteViewModel.addAndRemoveFavorite(data.id, 1)

        }


        Log.e(logKey, "setData")

    }





    private fun observeAndSetData(id: Int) {
        dialog.showDialog(resources.getString(R.string.DialogLoading))//show dialog
        binding.linearLayout4.visibility=View.INVISIBLE
        binding.bottomLayout.visibility=View.INVISIBLE//hide layouts

        //get Data
        viewModel.getData(id)

        viewModel.bodyLiveData.observe({ lifecycle }, { body ->
            dialog.dismissDialog()//dismiss dialog
            binding.linearLayout4.visibility=View.VISIBLE
            binding.bottomLayout.visibility=View.VISIBLE //show Layouts

            //set data
            bindData(body.data)

        })

    }




    }

