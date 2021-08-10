package com.os_tec.store.Activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.os_tec.store.Classes.ConnectivityReceiver
import com.os_tec.store.Classes.InternetAlertDialog
import com.os_tec.store.Classes.MyApp
import com.os_tec.store.Fragments.*
import com.os_tec.store.Fragments.Registration.ActivationCodeFragment
import com.os_tec.store.Fragments.Registration.LoginFragment
import com.os_tec.store.Fragments.Registration.SignupFragment
import com.os_tec.store.Fragments.Registration.WelcomeFragment
import com.os_tec.store.Fragments.Search.SearchFragment
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivityNavigation3Binding

class Navigation3Activity : AppCompatActivity(),ConnectivityReceiver.ConnectivityReceiverListener {
    lateinit var binding:ActivityNavigation3Binding
    lateinit var alertDialog:InternetAlertDialog
    val sliderFragment=SliderFragment()
    val welcomeFragment= WelcomeFragment()
    val signupFragment= SignupFragment()
    val loginFragment= LoginFragment()
    val ForgetPasswordFragment=ForgetPasswordFragment()
    val searchFragment= SearchFragment()
    val createAddressFragment= CreateAddressFragment()
    val resetPasswordFragment= ResetPasswordFragment()
    val activationCodeFragment=ActivationCodeFragment()
    val settingFragment=SettingFragment()
    var activeFragment:Fragment?= null
    var nv:String=""
    var nt=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNavigation3Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

         nv = intent.getStringExtra("nv").toString()
         alertDialog = InternetAlertDialog(this)

        val sliderArray = intent.getSerializableExtra("slider")

       //check internet connection
        ConnectivityReceiver.connectivityReceiverListener = this
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)


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

            "setting" -> {
                startFragment(settingFragment)
            }

            "slider" -> {
                supportActionBar!!.hide()
                val bundle=Bundle()
                bundle.putSerializable("slider",sliderArray)
                sliderFragment.arguments=bundle
                startFragment(sliderFragment)
            }

        }


    }

    override fun onBackPressed() {

        if (activeFragment == loginFragment || activeFragment == signupFragment) {
            startFragment(welcomeFragment)

        } else if (activeFragment == activationCodeFragment) {
            Toast.makeText(this, "Please Enter Verification Code", Toast.LENGTH_LONG).show()

        } else if (activeFragment == ForgetPasswordFragment) {
            startFragment(loginFragment)

        } else if (activeFragment == resetPasswordFragment) {
            startFragment(settingFragment)

        } else {
            ConnectivityReceiver.connectivityReceiverListener = null
            super.onBackPressed()

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
            onBackPressed()
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

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            alertDialog.showDialog(resources.getString(R.string.noInternet))
        }


    }




}