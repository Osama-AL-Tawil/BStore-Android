package com.os_tec.store.Fragments.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Model.CategoryObject
import com.os_tec.store.Model.ProductsDataModel
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.RetrofitApiInterface
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class HomeViewModel:ViewModel() {
    val logKey="HomeViewModel.OS"
    val categoryLiveData:MutableLiveData<ArrayList<CategoryObject>> = MutableLiveData()
    val categoryArrayList:ArrayList<CategoryObject> = ArrayList()

    val featuredProductsLiveData:MutableLiveData<ArrayList<ProductsDataModel>> = MutableLiveData()
    val featuredProductsArrayList:ArrayList<ProductsDataModel> = ArrayList()

    val bestSellProductsLiveData:MutableLiveData<ArrayList<ProductsDataModel>> = MutableLiveData()
    val bestSellProductsArrayList:ArrayList<ProductsDataModel> = ArrayList()

    private val service = ApiClient.getRetrofitInstance()!!.create(RetrofitApiInterface::class.java)

//@OptIn(DelicateCoroutinesApi::class)

    private fun getProductsData()= GlobalScope.launch {
        try {
            val apiResponse=service.getHomeProducts().awaitResponse()
            if (apiResponse.isSuccessful){
                if (apiResponse.body()!=null){
                    //get response body
                    val responseBody = apiResponse.body()!!
                    //get data
                    val categoriesData=responseBody.data.categories
                    val featuredProductsData=responseBody.data.featured_products
                    val bestSellProductsData=responseBody.data.best_sell_products
                    //add Data in Array
                    categoryArrayList.addAll(categoriesData)
                    featuredProductsArrayList.addAll(featuredProductsData)
                    bestSellProductsArrayList.addAll(bestSellProductsData)

                    logMessage(logKey, "getCategories")

                    //logMessage(logKey,"${responses.data}")

                }
            }else {
                logMessage(logKey,"Api Response : ${apiResponse.message()}")
            }

        }catch (e:Exception){

            logMessage(logKey,e.message.toString())

        }


        }




     @DelicateCoroutinesApi
      fun loadData() {

         GlobalScope.launch{
           getProductsData().join() // wait

           logMessage(logKey, "get")

             if (categoryArrayList.isNotEmpty()
                 &&featuredProductsArrayList.isNotEmpty()
                 &&bestSellProductsArrayList.isNotEmpty()
             ){
                 logMessage(logKey, "Post Data")
                 categoryLiveData.postValue(categoryArrayList)
                 featuredProductsLiveData.postValue(featuredProductsArrayList)
                 bestSellProductsLiveData.postValue(bestSellProductsArrayList)

             }else{
                 logMessage(logKey, "Something is wrong no data")

             }


         }


     }

//           GlobalScope.launch {
//               val j=getCategories()
//
//               delay(10000)
//               logMessage(logKey,categoryArrayList.toString())
//
//               if (categoryArrayList.isNotEmpty()){
//                   logMessage(logKey,"PostData")
//                   categoryLiveData.postValue(categoryArrayList)
//               }
//           }

//       runBlocking {
//           val cat=GlobalScope.async {
//               getCategories()
//               logMessage(logKey,"endLunch1")
//
//           }
//delay(3000)           //cat.join()
//            if (categoryArrayList.isNotEmpty()){
//               logMessage(logKey,"PostData")
//               categoryLiveData.postValue(categoryArrayList)
//           }
//               logMessage(logKey,"endLunch2")
//       }
//
//           logMessage(logKey,"EndBlock")


//         logMessage(logKey,"End")
//
//    }




    //        service.getCategories().enqueue(object : Callback<CategoriesResponseModel> {
//            override fun onResponse(
//                call: Call<CategoriesResponseModel>,
//                response: Response<CategoriesResponseModel>
//            ) {
//                if (response.body()!=null){
//                    logMessage(logKey, "getCategories")
//                    val responses = response.body()!!
//                    categoryArrayList.addAll(responses.data)
//                    //logMessage(logKey,"${responses.data}")
//                }
//
//
//
//            }
//
//            override fun onFailure(call: Call<CategoriesResponseModel>, t: Throwable) {
//                logMessage(logKey, t.message.toString())
//            }
//
//        })






//    GlobalScope.async{
//       service.getCategories().enqueue(object : Callback<CategoriesResponseModel> {
//            override fun onResponse(
//                call: Call<CategoriesResponseModel>,
//                response: Response<CategoriesResponseModel>
//            ) {
//                logMessage(logKey,"getCategories")
//                val responses=response.body()!!
//                categoryArrayList.addAll(responses.data)
//                //logMessage(logKey,"${responses.data}")
//
//
//            }
//
//            override fun onFailure(call: Call<CategoriesResponseModel>, t: Throwable) {
//                logMessage(logKey,t.message.toString())
//            }
//
//        })}



//    GlobalScope.async {
//       service.getCategories().enqueue(object :Callback<CategoriesResponseModel>{
//           override fun onResponse(
//               call: Call<CategoriesResponseModel>,
//               response: Response<CategoriesResponseModel>
//           ) {
//               logMessage(logKey,"getCategories")
//                val responses=response.body()!!
//                categoryArrayList.addAll(responses.data)
//                logMessage(logKey,"${responses.data}")
//           }
//
//           override fun onFailure(call: Call<CategoriesResponseModel>, t: Throwable) {
//               TODO("Not yet implemented")
//           }
//
//       })
//   }.await()


    private fun logMessage(logKeys:String,message:String){
        Log.e(logKeys,message)
    }
}