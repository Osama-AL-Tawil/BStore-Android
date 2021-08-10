package com.os_tec.store.Activities

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.ContextUtils
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.R
import com.os_tec.store.databinding.ActivitySplachBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SplashActivity : AppCompatActivity() {
    lateinit var binding:ActivitySplachBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplachBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val sh=SharedPreferences()




        if (sh.authorization()) {
            val sleep = object : Thread() {
                override fun run() {
                    sleep(500)
                    startActivity(Intent(this@SplashActivity, MainNavigation::class.java))
                    finish()
                    super.run()
                }
            }
            sleep.start()

                }else{
                    binding.progressBar.visibility=View.VISIBLE
                    ApiRepository(this@SplashActivity).getSlider("Slider")

                }


    }


    //Change and set App Language
    override fun attachBaseContext(newBase: Context?) {
        // get chosen language from shared preference
        val appLanguage = SharedPreferences().getAppLanguage()
        val localeToSwitchTo=Locale(appLanguage)
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase!!, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }



}