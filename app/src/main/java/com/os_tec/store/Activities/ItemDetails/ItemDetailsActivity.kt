package com.os_tec.store.Activities.ItemDetails

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Adapters.ImageViewPagerAdapter
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.CustomAlertDialog
import com.os_tec.store.Classes.SeeMoreTextView
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.Fragments.Favorite.FavoriteViewModel
import com.os_tec.store.Model.ProductsModel
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivityItemDetailsBinding
import kotlinx.coroutines.*
import java.util.*

@DelicateCoroutinesApi
class ItemDetailsActivity : AppCompatActivity() {
    private val logKey = "DetailsActivity.OS"
    lateinit var binding: ActivityItemDetailsBinding
    lateinit var viewModel: DetailsViewModel
    var isFavorite:Boolean=false
    var quantity=0
    var productId=0

    @DelicateCoroutinesApi
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
        //initialized ViewModel
        viewModel=DetailsViewModel() //DetailsViewModel



        //getAnd Set Data------------------------------------
        observeAndSetData(id)


        binding.btnBuyNow.setOnClickListener {
            ApiRepository(this).buy(logKey,productId,quantity)// buy
            val i=Intent(this,Navigation2Activity::class.java)
            i.putExtra("nv","confirmation")
            startActivity(i)
            finish()

        }


        binding.btnAddCart.setOnClickListener {
            addToCart(id,quantity)
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
//   private fun minCount(id: Int){
//       viewModel.getStock(this,id).observe({lifecycle},{stock->
//           if (stock>=count && stock!=0 && count>1){
//               val newCount=count-1
//               binding.count.text=newCount.toString()
//               count=newCount
//
//           }else{
//               Toast.makeText(this, resources.getString(R.string.lowestQuantity), Toast.LENGTH_SHORT).show()
//           }
//       })
//
//    }

//    private fun plusCount(id: Int) {
//         viewModel.getStock(this,id).observe({lifecycle},{stock->
//             if (stock>count && stock!=0){
//               val newCount=count+1
//               binding.count.text=newCount.toString()
//               count=newCount
//
//           }else {
//               Toast.makeText(this, resources.getString(R.string.stockOut), Toast.LENGTH_SHORT)
//                   .show()
//           }
//       })
//



// select quantity ------------------------------------------------------------------------------
   private fun minCount(){
           if (quantity>1){
               val newQuantity=quantity-1
               binding.count.text=newQuantity.toString()
               quantity=newQuantity

           }else{
               Toast.makeText(this, resources.getString(R.string.lowestQuantity), Toast.LENGTH_SHORT).show()
           }


    }

    private fun plusCount(stock: Int) {

             if (stock>=quantity){
               val newQuantity=quantity+1
               binding.count.text=newQuantity.toString()
                 quantity=newQuantity

           }else {
               Toast.makeText(this, resources.getString(R.string.stockOut), Toast.LENGTH_SHORT)
                   .show()
           }

        }




  fun addToCart(id: Int,Quantity:Int){
      CoroutineScope(Dispatchers.IO).launch {
          val stock=ApiRepository(this@ItemDetailsActivity).getStock(logKey,id)

          withContext(Dispatchers.Main){
              if (stock>quantity){
                  ApiRepository(this@ItemDetailsActivity).addToCart(logKey,id,Quantity)
              }else{
                  Toast.makeText(this@ItemDetailsActivity, resources.getString(R.string.currentQuantity)+":"+stock, Toast.LENGTH_SHORT)
                      .show()
              }
          }
      }


 }



    //Bind data in view
    private fun bindData(data: ProductsModel) {
        //bind data in view
        binding.textTitle.text = data.title
        //val currency=Currency.getInstance("USD").symbol
        val currency=Currency.getInstance(Locale("",Locale.US.country)).symbol
        binding.textPrice.text = " $currency ${data.price}"
        isFavorite=data.is_fav
        productId=data.id

        //ImageSlider
        val viewPager = binding.imageSlider
        val viewPagerAdapter =
            ImageViewPagerAdapter(this@ItemDetailsActivity, data.attachments)
        viewPager.adapter = viewPagerAdapter
        binding.indicator.viewPager = viewPager //set viewPager indicator ->Dots



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




        //set stock count
        if (data.stock !=0) {
            quantity=1
            binding.count.text = "1"
        }else{
            binding.count.text = "0"

        }
        //select quantity
        binding.btnMin.setOnClickListener {
            minCount()
        }

        binding.btnPlus.setOnClickListener {
            plusCount(data.stock)
        }





        //change favorite btn background
        if(isFavorite){
            changeBtnFavoriteBackground(true)
        }

        //add and remove from favorite
        binding.btnFavorite.setOnClickListener {
        addAndRemoveFavorite(data.id,isFavorite)
        }


        Log.e(logKey, "setData")

    }





    private fun observeAndSetData(id: Int) {
        binding.linearLayout4.visibility=View.INVISIBLE
        binding.bottomLayout.visibility=View.INVISIBLE//hide layouts

        //get Data
        viewModel.getItemDetails(this,id).observe({ lifecycle }, { body ->
            binding.linearLayout4.visibility=View.VISIBLE
            binding.bottomLayout.visibility=View.VISIBLE //show Layouts
            //set data
            bindData(body.data)

        })

    }

    private fun addAndRemoveFavorite(id: Int, is_favorite: Boolean) {

        if (is_favorite) { //if the product added in favorite ->remove
            ApiRepository(this@ItemDetailsActivity).addInFavorite(logKey, id, 0)
            changeBtnFavoriteBackground(false)
            isFavorite=false

        } else {//if product not added in favorite->add
            ApiRepository(this@ItemDetailsActivity).addInFavorite(logKey, id, 1)
            changeBtnFavoriteBackground(true)
            isFavorite=true

        }

    }




    private fun changeBtnFavoriteBackground(is_favorite:Boolean){
        if (is_favorite){
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_red)

        }else{
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_black)

        }

    }






    }

