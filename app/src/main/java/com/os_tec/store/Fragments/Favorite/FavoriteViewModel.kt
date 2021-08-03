package com.os_tec.store.Fragments.Favorite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Model.ProductsDataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel:ViewModel() {
    val logKey="FavoriteViewModel.OS"
    val favoriteLiveData:MutableLiveData<ArrayList<ProductsDataModel>> = MutableLiveData()
    private val service=ApiClient.getRetrofitInstance()!!.create(RetrofitApiInterface::class.java)



fun addAndRemoveFavorite(ads_id:Int,is_favorite:Int){
    val params=HashMap<String,Any>()
    params["ads_id"]=ads_id
    params["is_favorite"]=is_favorite

    service.addAndRemoveFavorite(params).enqueue(object :Callback<ProductsDataModel>{
        override fun onResponse(
            call: Call<ProductsDataModel>,
            response: Response<ProductsDataModel>
        ) {
            if (response.code()!=200&&response.body()!=null){
                Log.e(logKey,response.body().toString())

            }else{
                Log.e(logKey,response.errorBody()!!.string())

            }
        }

        override fun onFailure(call: Call<ProductsDataModel>, t: Throwable) {
            Log.e(logKey,t.message.toString())

        }

    })
}




    fun getFavorite(){
        val params=HashMap<String,Any>()
//        params["ads_id"]=ads_id
//        params["is_favorite"]=is_favorite

        service.getFavorite().enqueue(object :Callback<ProductsDataModel>{
            override fun onResponse(
                call: Call<ProductsDataModel>,
                response: Response<ProductsDataModel>
            ) {
                if (response.code()!=200&&response.body()!=null){
                    Log.e(logKey,response.body().toString())

                }else{
                    Log.e(logKey,response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<ProductsDataModel>, t: Throwable) {
                Log.e(logKey,t.message.toString())
            }

        })
    }


}