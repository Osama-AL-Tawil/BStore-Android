package com.os_tec.store.Classes

import android.app.Activity
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.os_tec.store.R

class Validation(val activity: Activity) {
    private var bool:Boolean=true
    private val logKey="ValidationClass.OS"


    fun validateEmail(value:String):Boolean {
         if (TextUtils.isEmpty(value)) {
            toastMessage(activity.resources.getString(R.string.EmptyField))
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
             toastMessage(activity.resources.getString(R.string.WrongEmail))

             return false
        }
        return bool
    }


    fun validateEditText(value:String):Boolean {
         if (TextUtils.isEmpty(value)) {
            toastMessage(activity.resources.getString(R.string.EmptyField))
            return false
        }
        return bool
    }

    fun validatePhone(phoneNumber:String):Boolean {
        if (phoneNumber.trim() == "") {
            toastMessage(activity.resources.getString(R.string.EmptyField))
            bool= false

        }else if (phoneNumber.length < 10) {
            toastMessage(activity.resources.getString(R.string.WrongPhone))
            bool= false

        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            toastMessage(activity.resources.getString(R.string.WrongPhone))

            bool= false
        }

        return bool
    }


    private fun toastMessage(message:String){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show()
    }

    fun logMessage(message:String){
        Log.e(logKey,message)

    }
}