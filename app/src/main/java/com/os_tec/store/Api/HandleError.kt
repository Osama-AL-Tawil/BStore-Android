package com.os_tec.store.Api

import android.text.TextUtils
import com.os_tec.store.Classes.MyApp
import com.os_tec.store.R
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class HandleError {
    val context=MyApp.appContext

    fun showError(e: Throwable): String? {
        var message: String? = ""
        try {
            if (e is IOException) {
                message = context.getString(R.string.noInternet)
                //message = "No Internet Connection"
            } else if (e is HttpException) {
                val error: HttpException = e as HttpException
                if (error.response()!!.code() == 401 || error.response()!!.code() == 419) {
                    val errorBody: String = error.response()!!.errorBody()!!.string()
                    val jsonObject = JSONObject(errorBody)
                    message = jsonObject.getString("message")

                } else if (error.response()!!.code() == 403) {
                    val errorBody: String = error.response()!!.errorBody()!!.string()
                    val jsonObject = JSONObject(errorBody)
                    message = jsonObject.getString("message")


                } else {
                    val errorBody: String = error.response()!!.errorBody()!!.string()
                    val jsonObject = JSONObject(errorBody)
                    message = jsonObject.getString("message")
                }
            } else {
                message = e.message
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
        if (TextUtils.isEmpty(message)) {
            message = "Unknown error occurred! Check LogCat."
        }
        return message
    }


    fun showErrorBody(response: Response<*>):String{
        var message = "null message"

            if (response.errorBody()!=null){
                val jsonObject=JSONObject(response.errorBody()!!.string())
                message=jsonObject.getString("message")
            }

      return message
    }



}