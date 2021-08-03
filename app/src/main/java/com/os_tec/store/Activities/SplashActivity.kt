package com.os_tec.store.Activities

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.os_tec.store.Classes.ContextUtils
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.R
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)

        val sh=SharedPreferences(this)

        val sleep=object :Thread(){
            override fun run() {
                sleep(500)

                if (sh.checkUser()){
                    startActivity(Intent(this@SplashActivity,MainNavigation::class.java))
                    finish()
                }else{
                    val i=Intent(this@SplashActivity,Navigation3Activity::class.java)
                    i.putExtra("nv","welcome")
                    startActivity(i)
                    finish()
                }

                super.run()
            }
        }
        sleep.start()
    }


    //Change and set App Language
    override fun attachBaseContext(newBase: Context?) {
        // get chosen language from shared preference
        val appLanguage = SharedPreferences(newBase!!).getAppLanguage()
        val localeToSwitchTo=Locale(appLanguage)
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }



}