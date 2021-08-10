package com.os_tec.store.Api

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.os_tec.store.Activities.MainNavigation
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Classes.ConnectivityReceiver
import com.os_tec.store.Classes.CustomAlertDialog
import com.os_tec.store.Classes.MyApp
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.Model.*
import com.os_tec.store.R
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception

class ApiRepository(val activity: Activity) {
    private val service = ApiClient.getRetrofitInstance().create(RetrofitApiInterface::class.java)
    private val context = MyApp.appContext
    private val alertDialog = CustomAlertDialog(activity)
    private var noInternet=activity.resources.getString(R.string.noInternet)
    var internetConnection=connectionStatus()



    fun signup(logKey: String, params: HashMap<String, Any>) {
        try {
            alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//ShowDialog
            //send login request
            service.signup(params).enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    //check response
                    if (response.code() == 200 && response.body() != null) {
                        mMessage(logKey, "Signup Success", response.body()!!.message)
                        //MoveToActivationFragment and send email to activation code Fragment
                        val email = response.body()!!.data.email
                        (activity as Navigation3Activity).startActivationFragment(email)

                    } else {//!=200
                        val message=HandleError().showErrorBody(response)
                        mMessage(logKey,message,message)
                    }

                    alertDialog.dismissDialog() //dismiss Dialog

                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    alertDialog.dismissDialog() //dismiss Dialog
                    val message= HandleError().showError(t).toString()
                    mMessage(logKey,"onFailure :$message",message)
                }

            })

        } catch (e: Exception) {

        }


    }



    @DelicateCoroutinesApi
    fun sendActivationCode(logKey: String, params: HashMap<String, Any>) {
        try {
            alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//ShowDialog
            service.activationCode(params).enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    if (response.code() == 200 && response.body() != null) {

                        //add user Data to SharedPreferences
                        SharedPreferences().addUserData(response.body()!!)

                        mMessage(logKey, "Accounts is Active", response.body()!!.message)
                        //Start Main Activity
                        activity.startActivity(Intent(activity, MainNavigation::class.java))
                        activity.finish()

                    } else {//!=200
                        val message=HandleError().showErrorBody(response)
                        mMessage(logKey,message,message)
                    }

                    alertDialog.dismissDialog() //dismissDialog

                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    alertDialog.dismissDialog() //dismiss Dialog
                    val message= HandleError().showError(t).toString()
                    mMessage(logKey,"onFailure :$message",message)
                }

            })
        } catch (e: Exception) {

        }
    }


    fun login(logKey: String, params: HashMap<String, Any>) {
        try {
            alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//ShowDialog

            service.login(params).enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    //check response
                    if (response.code() == 200 && response.body() != null) {

                        if (response.body()!!.status) {//check user status ,Active or not
                            //add userData in SharedPreferences
                            SharedPreferences().addUserData(response.body()!!)
                            mMessage(logKey, activity.getString(R.string.LoginSuccess), activity.getString(R.string.LoginSuccess))

                            //Start Main Activity
                            activity.startActivity(Intent(activity, MainNavigation::class.java))
                            activity.finish()

                        } else { //if the account notActivated
                            mMessage(logKey, response.body()!!.message, response.body()!!.message)
                        }

                    } else {//!=200
                        val message = HandleError().showErrorBody(response)
                        mMessage(logKey, message, message)
                    }

                    alertDialog.dismissDialog() //dismiss Dialog

                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    alertDialog.dismissDialog() //dismiss Dialog
                    val message = HandleError().showError(t).toString()
                    mMessage(logKey, "onFailure :$message", message)
                }

            })


        } catch (e: Exception) {

        }

    }

    fun logout(logKey: String) {
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//ShowDialog

            service.logout().enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    //check response
                    if (response.code() == 200) {
                            //remove userData in SharedPreferences
                            SharedPreferences().logout()
                            //Start Main Activity
                        val i=Intent(activity, Navigation3Activity::class.java)
                        i.putExtra("nv","welcome")
                            activity.startActivity(i)
                            activity.finish()

                    } else {//!=200
                        val message = HandleError().showErrorBody(response)
                        mMessage(logKey, message, message)
                    }
                    alertDialog.dismissDialog() //dismiss Dialog

                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    alertDialog.dismissDialog() //dismiss Dialog
                    val message = HandleError().showError(t).toString()
                    mMessage(logKey, "onFailure :$message", message)
                }

            })


    }


    suspend fun forgetPassword(logKey: String,params: HashMap<String, Any>):Boolean {
        var status=false
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//ShowDialog
            val response=service.forgetPassword(params).awaitResponse()

                if (response.isSuccessful){
                    status=true
                    val message=response.body()!!.message
                    mMessage(logKey, message, message)
                }else{
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)
                }

                alertDialog.dismissDialog() //dismiss Dialog

        return status
    }



    suspend fun resetPassword(logKey: String,params: HashMap<String, Any>):Boolean {
        var status=false
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//ShowDialog
            val response=service.resetPassword(params).awaitResponse()

                if (response.isSuccessful){
                    status=true
                    val message=response.body()!!.message
                    mMessage(logKey, message, message)
                }else{
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)
                }

                alertDialog.dismissDialog() //dismiss Dialog

        return status
    }








 fun searchAndCategory(logKey: String,params: MutableMap<String, Any>,dialog:Boolean):MutableLiveData<ArrayList<ProductsModel>>{
    val data:MutableLiveData<ArrayList<ProductsModel>> = MutableLiveData()
     if (dialog){
         alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//show Dialog

     }

    service.searchAndCategory(params).enqueue(object :Callback<SearchResponseModel>{
        override fun onResponse(
            call: Call<SearchResponseModel>,
            response: Response<SearchResponseModel>
        ) {
            if (response.code() == 200 && response.body()!=null) {
                data.postValue(response.body()!!.data)

            } else {//!=200
                val message=HandleError().showErrorBody(response)
                mMessage(logKey,message,message)
            }

            if (dialog){
                alertDialog.dismissDialog()//dismiss Dialog
            }
        }

        override fun onFailure(call: Call<SearchResponseModel>, t: Throwable) {
            if (dialog){
                alertDialog.dismissDialog()//dismiss Dialog
            }
            val message= HandleError().showError(t).toString()
            mMessage(logKey,"onFailure :$message",message)

        }

    })
    return data
}



    fun getHomeData(logKey: String): MutableLiveData<ProductsResponseModel> {
        val data: MutableLiveData<ProductsResponseModel> = MutableLiveData()
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//show Dialog

        service.getHomeData().enqueue(object : Callback<ProductsResponseModel> {
            override fun onResponse(
                call: Call<ProductsResponseModel>,
                response: Response<ProductsResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    data.postValue(response.body())

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    data.postValue(ProductsResponseModel(false,message,
                        DataObject(ArrayList(), ArrayList(), arrayListOf())
                    ))
                    mMessage(logKey, message, message)

                }
                alertDialog.dismissDialog() //dismissDialog

            }

            override fun onFailure(call: Call<ProductsResponseModel>, t: Throwable) {
                alertDialog.dismissDialog() //dismissDialog
                val message = HandleError().showError(t).toString()
                data.postValue(ProductsResponseModel(false,message,
                    DataObject(ArrayList(), ArrayList(), arrayListOf())
                ))
                mMessage(logKey, "onFailure :$message", message)

            }
        })

        return data
    }





    fun getItemDetails(logKey: String,id: Int):MutableLiveData<DetailsResponseModel> {
        val data:MutableLiveData<DetailsResponseModel> = MutableLiveData()
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//show Dialog

        service.getProductDetails(id).enqueue(object :Callback<DetailsResponseModel>{
            override fun onResponse(
                call: Call<DetailsResponseModel>,
                response: Response<DetailsResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    data.postValue(response.body())

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)
                }

                alertDialog.dismissDialog() //dismissDialog

            }

            override fun onFailure(call: Call<DetailsResponseModel>, t: Throwable) {
                alertDialog.dismissDialog() //dismissDialog
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }


        })

       return data
    }


    suspend fun getStock(logKey: String, id: Int): Int {
        var stock = 0
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getProductDetails(id).awaitResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (response.code() == 200 && response.body() != null) {
                        stock = response.body()!!.data.stock
//                        Log.e("Stock.OS",stock.toString())
                    } else {//!=200
                        val message = HandleError().showErrorBody(response)
                        mMessage(logKey, message, message)
                    }
                } else {
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)
                }
            }
        }.join()

//        Log.e("Stock.OS","return data")

        return stock
    }





    fun getFavorite(logKey: String): MutableLiveData<ArrayList<ProductsModel>> {
        val data: MutableLiveData<ArrayList<ProductsModel>> = MutableLiveData()
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//show Dialog

        service.getFavorite().enqueue(object : Callback<FavoriteResponseModel> {
            override fun onResponse(
                call: Call<FavoriteResponseModel>,
                response: Response<FavoriteResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    data.postValue(response.body()!!.data)

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)
                }
                alertDialog.dismissDialog()//dismissDialog
            }

            override fun onFailure(call: Call<FavoriteResponseModel>, t: Throwable) {
                alertDialog.dismissDialog() //dismissDialog
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

        return data
    }




    //add in favorite  Add->1  , Remove->0
    fun addInFavorite(logKey: String, ads_id: Int, is_favorite: Int): MutableLiveData<Boolean> {
        val boolean: MutableLiveData<Boolean> = MutableLiveData()

        val params = HashMap<String, Any>()
        params["ads_id"] = ads_id
        params["is_favorite"] = is_favorite

        service.addAndRemoveFavorite(params).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    mMessage(
                        logKey,
                        "Add In Favorite  ${response.body()!!.message}",
                        response.body()!!.message
                    )


                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }

        })

        return boolean
    }




    fun getCartData(logKey: String): MutableLiveData<CartResponseModel> {
        val data: MutableLiveData<CartResponseModel> = MutableLiveData()
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//show Dialog

        service.getCart().enqueue(object : Callback<CartResponseModel> {
            override fun onResponse(
                call: Call<CartResponseModel>,
                response: Response<CartResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    data.postValue(response.body())

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }
                alertDialog.dismissDialog() //dismissDialog

            }

            override fun onFailure(call: Call<CartResponseModel>, t: Throwable) {
                alertDialog.dismissDialog() //dismissDialog
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

        return data
    }




    fun addToCart(logKey: String,product_id: Int,quantity: Int) {
        val params=HashMap<String,Any>()
        params["product_id"]=product_id
        params["quantity"]=quantity

        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//show Dialog

        service.addToCart(params).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val message = response.body()!!.message
                    mMessage(logKey,message,message)

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }
                alertDialog.dismissDialog() //dismissDialog

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                alertDialog.dismissDialog() //dismissDialog
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

    }




    fun updateCart(logKey: String,cart_id: Int,quantity: Int) {
        val params=HashMap<String,Any>()
        params["cart_id"]=cart_id
        params["quantity"]=quantity

        service.updateCart(params).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val message = response.body()!!.message
                    mMessage(logKey,message,message)

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

    }




    fun deleteFromCart(logKey: String,cartId: Int) {

        service.deleteFromCart(cartId).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val message = response.body()!!.message
                    mMessage(logKey,message,message)

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

    }

    fun payment(logKey: String,params:HashMap<String,Any>) {

        service.payment(params).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val message = response.body()!!.message
                    mMessage(logKey,message,message)

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

    }


    fun buy(logKey: String,productId:Int,quantity:Int) {
        val params=HashMap<String,Any>()
        params["product_id"]=productId
        params["quantity"]=quantity

        service.buy(params).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val message = response.body()!!.message
                    mMessage(logKey,message,message)

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

    }



    fun getMyOrder(logKey: String): MutableLiveData<MyOrderResponseModel> {
        val data: MutableLiveData<MyOrderResponseModel> = MutableLiveData()
        alertDialog.showDialog(activity.resources.getString(R.string.DialogLoading))//show Dialog

        service.getMyOrder().enqueue(object : Callback<MyOrderResponseModel> {
            override fun onResponse(
                call: Call<MyOrderResponseModel>,
                response: Response<MyOrderResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    data.postValue(response.body())

                } else {//!=200
                    val message = HandleError().showErrorBody(response)
                    mMessage(logKey, message, message)

                }
                alertDialog.dismissDialog() //dismissDialog

            }

            override fun onFailure(call: Call<MyOrderResponseModel>, t: Throwable) {
                alertDialog.dismissDialog() //dismissDialog
                val message = HandleError().showError(t).toString()
                mMessage(logKey, "onFailure :$message", message)
            }
        })

        return data
    }



     fun getSlider(logKey: String) {
        if (internetConnection) {
            service.getSlider().enqueue(object : Callback<SliderResponseModel> {
                override fun onResponse(
                    call: Call<SliderResponseModel>,
                    response: Response<SliderResponseModel>
                ) {
                    //send data to Navigation3Activity
                    val i = Intent(activity, Navigation3Activity::class.java)
                    i.putExtra("nv", "slider")
                    i.putExtra("slider", response.body()!!.data)
                    activity.startActivity(i)
                    activity.finish() //finish SplashActivity
                }

                override fun onFailure(call: Call<SliderResponseModel>, t: Throwable) {
                    val message = HandleError().showError(t).toString()
                    mMessage(logKey, "onFailure :$message", message)
                }

            })

        }else{
            mMessage("ApiRepository",noInternet,noInternet)
        }


    }



    fun mMessage(logKey:String,logMessage:String,toastMessage:String){
        Toast.makeText(activity,toastMessage, Toast.LENGTH_LONG).show()
        Log.e(logKey,logMessage)
    }

    fun connectionStatus(): Boolean {
        return ConnectivityReceiver().isNetworkAvailable(activity)

    }


}