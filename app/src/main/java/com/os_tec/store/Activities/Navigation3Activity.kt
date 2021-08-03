package com.os_tec.store.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.os_tec.store.Fragments.*
import com.os_tec.store.Fragments.Registration.ActivationCodeFragment
import com.os_tec.store.Fragments.Registration.LoginFragment
import com.os_tec.store.Fragments.Registration.SignupFragment
import com.os_tec.store.Fragments.Registration.WelcomeFragment
import com.os_tec.store.Fragments.Search.SearchFragment
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivityNavigation3Binding

class Navigation3Activity : AppCompatActivity() {
    lateinit var binding:ActivityNavigation3Binding
    val welcomeFragment= WelcomeFragment()
    val signupFragment= SignupFragment()
    val loginFragment= LoginFragment()
    val searchFragment= SearchFragment()
    val createAddressFragment= CreateAddressFragment()
    val activationCodeFragment=ActivationCodeFragment()
    var activeFragment:Fragment?= null
    var nv:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNavigation3Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

         nv = intent.getStringExtra("nv").toString()


        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        //start Fragment
       // startFragment(welcomeFragment)

        when (nv) {

            "welcome" -> {
                supportActionBar!!.hide()
              startFragment(welcomeFragment)
            }

            "addAddress" -> {
                startFragment(createAddressFragment)
            }

            "search" -> {
                supportActionBar!!.hide()
                startFragment(searchFragment)
            }

        }


    }

    override fun onBackPressed() {

        if (activeFragment == loginFragment || activeFragment == signupFragment) {
            startFragment(welcomeFragment)

        } else if (activeFragment == activationCodeFragment) {
            Toast.makeText(this, "Please Enter Verification Code", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

                if (activeFragment == loginFragment || activeFragment == signupFragment) {
                    startFragment(welcomeFragment)

                } else {
                    finish()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }



    fun startActivationFragment(email:String){
        supportActionBar!!.hide()
        val bundle=Bundle()
        bundle.putString("email",email)
        activationCodeFragment.arguments=bundle
       startFragment(activationCodeFragment)

    }

     fun startFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container3,fragment).commit()
         activeFragment=fragment
    }

}