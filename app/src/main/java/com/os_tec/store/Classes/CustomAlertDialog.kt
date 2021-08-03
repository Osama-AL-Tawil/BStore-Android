package com.os_tec.store.Classes

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import com.os_tec.store.R
import com.os_tec.store.databinding.CsDialogBinding

class CustomAlertDialog (val activity: Activity){
    private lateinit var alertDialog: AlertDialog
    private val view= LayoutInflater.from(activity).inflate(R.layout.cs_dialog,null,false)!!
    private var  binding:CsDialogBinding= CsDialogBinding.bind(view)
    private val  builder= AlertDialog.Builder(activity, R.style.AlertDialog)

    fun showDialog(text:String){
        builder.setView(binding.root)
        builder.setCancelable(false)
        binding.textViewDialog.text=text

        alertDialog= builder.create()
        alertDialog.show()
    }

    private fun removeView(view:View) {
        val parent = view.parent as ViewGroup
        parent.removeView(view)
    }

    fun dismissDialog(){
        if (alertDialog.isShowing){
            alertDialog.dismiss()
            removeView(view)

        }





   }


}