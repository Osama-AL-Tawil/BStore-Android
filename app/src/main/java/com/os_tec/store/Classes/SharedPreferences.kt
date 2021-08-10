package com.os_tec.store.Classes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.os_tec.store.Model.BodyObject
import com.os_tec.store.Model.RegistrationResponse
import com.os_tec.store.R
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.set


class SharedPreferences() {
    private val logKey="SharedPreferences.OS"
    val context=MyApp.appContext
    private val userData=context.getSharedPreferences("userData",Context.MODE_PRIVATE)
    private val appData=context.getSharedPreferences("appData",Context.MODE_PRIVATE)
    private val data=context.getSharedPreferences("data",Context.MODE_PRIVATE)


    fun addUserData(body:RegistrationResponse){
        userData.edit()

            .putBoolean("status",body.status)
            .putInt("id",body.data.id)
            .putString("name",body.data.name)
            .putString("email",body.data.email)
            .putString("mobile",body.data.mobile)
            .putInt("is_activated",body.data.is_activated)
            .putString("lat",body.data.lat.toString())
            .putString("lng",body.data.lng.toString())
            .putString("address",body.data.address)
            .putString("api_token",body.data.api_token)
            .apply()

        //logMessage("addUserData : $body")
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
                userData.getString("lat","00.00").toString().toDouble(),
                userData.getString("lng","00.00").toString().toDouble(),
                userData.getString("address", context.resources.getString(R.string.NotAvalibale)).toString(),
                userData.getString("api_token",context.resources.getString(R.string.NotAvalibale)).toString(),
            )
        )
    }



    fun authorization():Boolean{
        val id=userData.getInt("id",0)
        val token=userData.getString("api_token","")
        //logMessage("check user if login -> Id : $id, Token : $token")

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


    fun addCartIds(listOfIds:ArrayList<String>){
        val shData=data.edit()
        val cartIds = HashSet<String>()
        cartIds.addAll(listOfIds)
        Log.e("CartIDS",cartIds.toString())

        shData.putStringSet("cart_ids", cartIds)

        shData.apply()

    }


    fun getCartIds():HashMap<String,Any>{
        val shData=data.getStringSet("s", HashSet<String>())
//        val idsArray=ArrayList<String>()
//        idsArray.addAll(shData!!)
        val hash=HashMap<String,Any>()
        hash["cart_ids"]=shData as HashSet<String>
        Log.e("CartIDS",hash.toString())


        return hash

    }


    fun saveIdsArray(list: ArrayList<String?>?) {
        val editor=data.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("cart_ids", json)
        Log.e("CartIDS","SAVE : JsonIds : $json")

        editor.apply() // This line is IMPORTANT !!!
    }

    fun getIdsArray(): ArrayList<String?>? {
        val gson = Gson()
        val json: String = data.getString("cart_ids","null")!!
        val type = object : TypeToken<ArrayList<String?>?>() {}.type

        return gson.fromJson(json, type)
    }


    fun removeIds(){
        data.edit().clear().apply()
        Log.e("CartIDS","removeCartIDS")

    }
}