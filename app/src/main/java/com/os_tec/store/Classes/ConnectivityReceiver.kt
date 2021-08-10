package com.os_tec.store.Classes

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.os_tec.store.R

class ConnectivityReceiver():BroadcastReceiver() {
    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("ConnectivityReceiver","onReceive")

        if (connectivityReceiverListener != null) {
            Log.e("ConnectivityReceiver","connectivityReceiverListener")
            connectivityReceiverListener!!.onNetworkConnectionChanged(isNetworkAvailable(context!!))
        }

    }


     fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }



     fun checkInternet(activity: Activity):Boolean{
        val status=ConnectivityReceiver().isNetworkAvailable(activity)
        val message=activity.resources.getString(R.string.noInternet)
        if (!status) {
         Toast.makeText(activity,message,Toast.LENGTH_LONG).show()
        }
        return status
    }


    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }






}