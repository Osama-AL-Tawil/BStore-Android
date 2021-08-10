package com.os_tec.store.Activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.os_tec.store.Adapters.NavigationAdapter
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.ConnectivityReceiver
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.Fragments.*
import com.os_tec.store.Fragments.Favorite.FavoriteFragment
import com.os_tec.store.Fragments.Home.HomeFragment
import com.os_tec.store.Fragments.MyOrder.MyOrderFragment
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivityNavigationBinding
import com.os_tec.store.databinding.BottomSheetBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlin.collections.ArrayList

@DelicateCoroutinesApi
class MainNavigation : AppCompatActivity() {
    var drawerLayout:DrawerLayout?=null
    private var nvDrawer:NavigationView?=null
    private var toolbar:Toolbar?=null
    lateinit var binding:ActivityNavigationBinding

    var activeFragment:Fragment?=null
    var homeFragment: HomeFragment = HomeFragment()
    var profileFragment=ProfileFragment()
    var myOrderFragment: MyOrderFragment = MyOrderFragment()
    var favoriteFragment: FavoriteFragment = FavoriteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


            // Find our drawer view
            drawerLayout = binding.drawerLayout
            nvDrawer = binding.nvView

            // Set a Toolbar
            toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            //set toolbar options
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)



            //set Navigation Drawer
            setNavigationDrawer()

            //start fragment
            startFragment(homeFragment)


            //close Drawer
            binding.btnCloseNav.setOnClickListener {
                drawerLayout!!.closeDrawer(GravityCompat.START)

            }








    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
             android.R.id.home->{
               drawerLayout!!.openDrawer(GravityCompat.START)
             }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(activeFragment!=homeFragment){
            startFragment(homeFragment)
        }else{
            super.onBackPressed()
        }
    }



    private fun setNavigationDrawer(){
        //set Navigation Layout
        binding.rcNavigationView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // ArrayList Items for rcNavigationDrawer
        val nvArray = ArrayList<String>()
        nvArray.addAll(resources.getStringArray(R.array.navigation))

        //set data and adapter
        val adapter = NavigationAdapter(this, nvArray)
        binding.rcNavigationView.adapter = adapter
    }




    @RequiresApi(Build.VERSION_CODES.M)
    fun navigateToFragment(nv: Int) {
        when (nv) {
            0 -> {//homeFragment
                startFragment(homeFragment)
            }

            1 -> {//ProfileFragment
                startFragment(profileFragment)
            }

            2 -> {//CartFragment
                if (ConnectivityReceiver().checkInternet(this)){
                    startFragment(homeFragment)
                    val i = Intent(this, Navigation2Activity::class.java)
                    i.putExtra("nv", "cart")
                    startActivity(i)
                }
            }

            3 -> {//FavoriteFragment
                if (ConnectivityReceiver().checkInternet(this)){
                    startFragment(favoriteFragment)
                }
            }

            4 -> {//MyOrderFragment
                if (ConnectivityReceiver().checkInternet(this)){
                    startFragment(myOrderFragment)

                }
            }

            5 -> {//Language
             showBottomSheet()
            }

            6 -> {//Settings
                startFragment(homeFragment)
                val i = Intent(this, Navigation3Activity::class.java)
                i.putExtra("nv", "setting")
                startActivity(i)
            }

            7 -> {//Logout
                if (ConnectivityReceiver().checkInternet(this)){
                    ApiRepository(this).logout("logout")

                }
            }

        }

        drawerLayout!!.closeDrawer(GravityCompat.START)

    }


    private fun startFragment(fragment: Fragment) {
        if (fragment.isAdded) {
            if(fragment!=activeFragment){
                supportFragmentManager.beginTransaction().remove(activeFragment!!).hide(activeFragment!!).commit()
                supportFragmentManager.beginTransaction().show(fragment).commit()
                activeFragment=fragment
            }

        } else {
            supportFragmentManager.beginTransaction().add(R.id.mainContainer, fragment).commit()
            if (activeFragment != null) {
                supportFragmentManager.beginTransaction().hide(activeFragment!!).commit()
            }
            activeFragment=fragment

        }
    }




  //bottom sheet to change app language

   private fun showBottomSheet(){
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet,null)
        val binding=BottomSheetBinding.bind(view)
        var language=""

        val dialog = BottomSheetDialog(this,R.style.CustomBottomSheetDialog)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()


         //get current language and select button
        when(SharedPreferences().getAppLanguage()){
            "en"->{
                checkedButton(binding.btnEnglish)
                unCheckedButton(binding.btnArabic)
                Log.e("HomeFragment","en")
            }
            "ar"->{
                checkedButton(binding.btnArabic)
                unCheckedButton(binding.btnEnglish)
                Log.e("HomeFragment","ar")

            }
        }



        binding.btnOk.setOnClickListener {
            //store app language value in SharedPreferences
            Log.e("HomeFragment",language)

            SharedPreferences().setAppLanguage(language)
            dialog.dismiss()
            //restart App
            startActivity(Intent(applicationContext, SplashActivity::class.java))
            finish()
        }


        binding.btnEnglish.setOnClickListener {
            //set and change buttons background and textColor
            checkedButton(binding.btnEnglish)
            unCheckedButton(binding.btnArabic)
            //add language in Variable -> en -> English
            language="en"
            Toast.makeText(this, resources.getString(R.string.English), Toast.LENGTH_SHORT).show()

        }


        binding.btnArabic.setOnClickListener {
            //set and change buttons background and textColor
            checkedButton(binding.btnArabic)
            unCheckedButton(binding.btnEnglish)
            //add language in Variable -> ar -> Arabic
            language="ar"
            Toast.makeText(this, resources.getString(R.string.Arabic), Toast.LENGTH_SHORT).show()


        }



    }

    private fun checkedButton(button:MaterialButton){
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.blue1))
        button.setTextColor(ContextCompat.getColor(this,R.color.white))
    }

    private fun unCheckedButton(button:MaterialButton){
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.gray3))
        button.setTextColor(ContextCompat.getColor(this,R.color.black))
    }



}
