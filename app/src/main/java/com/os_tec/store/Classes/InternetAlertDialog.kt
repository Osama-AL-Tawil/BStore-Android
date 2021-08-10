package com.os_tec.store.Classes

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Toast
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.R
import com.os_tec.store.databinding.CsDialogBinding
import com.os_tec.store.databinding.ErrorLayoutBinding
import java.lang.RuntimeException

class InternetAlertDialog (val activity: Activity){
    private lateinit var alertDialog: AlertDialog
    private val view= LayoutInflater.from(activity).inflate(R.layout.error_layout,null,false)!!
    private var  binding= ErrorLayoutBinding.bind(view)
    private val  builder= AlertDialog.Builder(activity, R.style.AlertDialog)

    fun showDialog(text:String){
        builder.setView(binding.root)
        builder.setCancelable(false)
        alertDialog= builder.create()
        alertDialog.show()

        val message=activity.resources.getString(R.string.noInternet)
        binding.errorMessage.text=message

        binding.btnAction.setOnClickListener {
            if (ConnectivityReceiver().isNetworkAvailable(activity)){
                alertDialog.dismiss()
                removeView(view)
            }else{
                Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
            }

        }
       // alertDialog.window!!.setGravity(Gravity.CENTER)

    }

    private fun removeView(view:View) {
        val parent = view.parent as ViewGroup
        parent.removeView(view)
    }

    fun dismissDialog(){
        try {
            if (alertDialog.isShowing){
                alertDialog.dismiss()
                removeView(view)

            }
        }catch (r:RuntimeException){

        }






   }


}