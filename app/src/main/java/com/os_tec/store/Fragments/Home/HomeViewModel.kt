package com.os_tec.store.Fragments.Home

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Model.CategoryModel
import com.os_tec.store.Model.ProductsModel
import com.os_tec.store.Model.ProductsResponseModel
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class HomeViewModel:ViewModel() {
    val logKey="HomeViewModel.OS"
    val categoryLiveData:MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()
    val categoryArrayList:ArrayList<CategoryModel> = ArrayList()

    val featuredProductsLiveData:MutableLiveData<ArrayList<ProductsModel>> = MutableLiveData()
    val featuredProductsArrayList:ArrayList<ProductsModel> = ArrayList()

    val bestSellProductsLiveData:MutableLiveData<ArrayList<ProductsModel>> = MutableLiveData()
    val bestSellProductsArrayList:ArrayList<ProductsModel> = ArrayList()

    private val service = ApiClient.getRetrofitInstance().create(RetrofitApiInterface::class.java)

//
//    private fun getProductsData()= GlobalScope.launch {
//            try {
//                val apiResponse=service.getHomeData().awaitResponse()
//                if (apiResponse.isSuccessful){
//
//                    if (apiResponse.body()!=null){
//                        //get response body
//                        val response = apiResponse.body()!!.data
//                        //add Data in Array
//                        categoryArrayList.addAll(response.categories)
//                        featuredProductsArrayList.addAll(response.featured_products)
//                        bestSellProductsArrayList.addAll(response.best_sell_products)
//
//                        logMessage(logKey, "getCategories")
//
//                    }
//
//                }else {
//                    logMessage(logKey,"Api Response : ${apiResponse.message()}")
//                }
//
//            }catch (e:Exception){
//                logMessage(logKey,"OnFailure ${e.message}")
//            }
//
//
//        }




      fun loadData(activity: Activity) :MutableLiveData<ProductsResponseModel>{

        return ApiRepository(activity).getHomeData(logKey) //get Data
     }


//    suspend fun  getProductsData(){
//        try {
//            CoroutineScope(Dispatchers.IO).launch{
//                val apiResponse=service.getHomeProducts()
//
//                withContext(Dispatchers.Main){
//                    if (apiResponse.isSuccessful){
//                        if (apiResponse.body()!=null){
//                            //get response body
//                            val body = apiResponse.body()!!.data
//                            //add Data in Array
//                            categoryArrayList.addAll(body.categories)
//                            featuredProductsArrayList.addAll(body.featured_products)
//                            bestSellProductsArrayList.addAll(body.best_sell_products)
//
//                            logMessage(logKey, "getCategories")
//
//
//                        }
//                    }else {
//                        logMessage(logKey,"Api Response : ${apiResponse.message()}")
//                    }
//
//                }
//
//            } .join()
//        }catch (e:Exception){
//
//        }
//
//    }


    private fun logMessage(logKeys:String,message:String){
        Log.e(logKeys,message)
    }
}