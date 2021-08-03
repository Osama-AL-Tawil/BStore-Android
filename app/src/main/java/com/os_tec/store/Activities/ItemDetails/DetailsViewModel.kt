package com.os_tec.store.Activities.ItemDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Model.DetailsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel:ViewModel() {
    private val logKey="DetailsViewModel.OS"
    val bodyLiveData:MutableLiveData<DetailsResponseModel> = MutableLiveData()
    private val service = ApiClient.getRetrofitInstance()!!.create(RetrofitApiInterface::class.java)


      fun getData(id: Int) {
         service.getProductDetails(id).enqueue(object :Callback<DetailsResponseModel>{
            override fun onResponse(
                call: Call<DetailsResponseModel>,
                response: Response<DetailsResponseModel>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    Log.e(logKey, "getData")
                    bodyLiveData.postValue(response.body())

                } else {//!=200
                    Log.e(logKey, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<DetailsResponseModel>, t: Throwable) {
                Log.e(logKey,"On Failure : ${t.message}")
            }


        })

}




}