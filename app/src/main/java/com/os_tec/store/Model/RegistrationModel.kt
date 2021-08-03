package com.os_tec.store.Model

import com.google.gson.annotations.SerializedName

//data class LoginBody(
//   // @Query("email") val email: JSONObject, @Query("password") val password: JSONObject
//   // @SerializedName("data") val hashMap: HashMap<String, String>
//    @SerializedName("email")val email: JSONObject,
//    @SerializedName("password") val password: JSONObject
//)

//data class SignupBody(val hashMap: HashMap<Any, Any>)

data class Test(
    @SerializedName("status")  val status:Boolean=false,
    @SerializedName("message") val message:String="")


data class RegistrationResponse(
    @SerializedName("status") val status:Boolean,
    @SerializedName("message") val message:String,
    @SerializedName("data") val body:BodyObject
)

data class BodyObject(
    @SerializedName("id") val id:Int,
    @SerializedName("email") val email:String,
    @SerializedName("name") val name:String,
    @SerializedName("mobile") val mobile:String,
    @SerializedName("is_activated") val is_activated:Int,
    @SerializedName("lat") val lat:Double,
    @SerializedName("lng") val lng:Double,
    @SerializedName("address") val address:String,
    @SerializedName("api_token") val api_token:String,
    )