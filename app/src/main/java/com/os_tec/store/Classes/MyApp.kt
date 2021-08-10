package com.os_tec.store.Classes

import android.app.Application
import android.content.*

class MyApp: Application() {
companion object{
    lateinit  var appContext: Context

}
    override fun onCreate() {
        super.onCreate()
        appContext= applicationContext

//        //check internet connection
//        ConnectivityReceiver.connectivityReceiverListener = this
//        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
//        val appLanguage = SharedPreferences().getAppLanguage()
//        val localeToSwitchTo= Locale(appLanguage)
//        val confg= appContext.resources.configuration
//        confg.setLocale(localeToSwitchTo)
//        appContext.createConfigurationContext(confg)

    }

//
//    override fun onNetworkConnectionChanged(isConnected: Boolean) {
//        if (isConnected){
//        Log.e("Internet533","connected")
//      }else{
//          val alertDialog=InternetAlertDialog(appContext)
//            alertDialog.showDialog("No enternt")
////          val intent=Intent(appContext,InternetActivity::class.java)
////
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////          startActivity(intent)
//        Log.e("Internet533","Not connected")
//    }
//}


}


