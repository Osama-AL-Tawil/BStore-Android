package com.os_tec.store.Classes

import android.content.Context
import android.util.Log
import com.os_tec.store.Model.BodyObject
import com.os_tec.store.Model.RegistrationResponse
import com.os_tec.store.R

class SharedPreferences(val context: Context) {
    private val logKey="SharedPreferences.OS"
    private val userData=context.getSharedPreferences("userData",Context.MODE_PRIVATE)
    private val appData=context.getSharedPreferences("appData",Context.MODE_PRIVATE)


    fun addUserData(data:RegistrationResponse){
        userData.edit()

            .putBoolean("status",data.status)
            .putInt("id",data.body.id)
            .putString("name",data.body.name)
            .putString("email",data.body.email)
            .putString("mobile",data.body.mobile)
            .putInt("is_activated",data.body.is_activated)
            .putString("lat",data.body.lat.toString())
            .putString("lng",data.body.lng.toString())
            .putString("address",data.body.address)
            .putString("api_token",data.body.api_token)
            .apply()

        logMessage("addUserData : $data")
    }


    fun getUserData():RegistrationResponse{
        logMessage("getUserData")
        return RegistrationResponse(
            userData.getBoolean("status",false),
            "",
            BodyObject(
                userData.getInt("id", 0),
                userData.getString("name",context.resources.getString(R.string.NotAvalibale)).toString(),
                userData.getString("email", context.resources.getString(R.string.NotAvalibale)).toString(),
                userData.getString("mobile",context.resources.getString(R.string.NotAvalibale)).toString(),
                userData.getInt("is_activated",200),
                userData.getString("lat",context.resources.getString(R.string.NotAvalibale)).toString().toDouble(),
                userData.getString("lng",context.resources.getString(R.string.NotAvalibale)).toString().toDouble(),
                userData.getString("address", context.resources.getString(R.string.NotAvalibale)).toString(),
                userData.getString("api_token",context.resources.getString(R.string.NotAvalibale)).toString(),
            )
        )
    }



    fun checkUser():Boolean{
        val id=userData.getInt("id",0)
        val token=userData.getString("api_token","")
        logMessage("check user if login -> Id : $id, Token : $token")

        return id!=0&&token!=""
    }


    fun logout(){
        userData.edit().clear().apply()
        logMessage("Delete userData ->Logout")
    }



    fun setAppLanguage(text:String){
      appData.edit().putString("language",text).apply()
        logMessage("setAppLanguage -> $text")

    }
    fun getAppLanguage():String{
      return appData.getString("language","ar").toString()

    }



    private fun logMessage(message:String){
        Log.e(logKey,message)

    }

}