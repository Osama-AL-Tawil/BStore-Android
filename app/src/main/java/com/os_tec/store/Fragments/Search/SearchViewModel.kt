package com.os_tec.store.Fragments.Search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Model.ProductsDataModel
import com.os_tec.store.Model.SearchResponseModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class SearchViewModel:ViewModel() {
    val logKey="SearchViewModel.OS"
    val searchLiveData:MutableLiveData<ArrayList<ProductsDataModel>> = MutableLiveData()
    private val service=ApiClient.getRetrofitInstance()!!.create(RetrofitApiInterface::class.java)
    @DelicateCoroutinesApi
    fun search(title:String){

             service.search(title).enqueue(object :Callback<SearchResponseModel>{
                 override fun onResponse(
                     call: Call<SearchResponseModel>,
                     response: Response<SearchResponseModel>
                 ) {
                         if (response.code()==200 && response.body()!=null){
                             searchLiveData.postValue(response.body()!!.data)//post data
                             Log.e(logKey,"$searchLiveData")

                         }else{//!=200
                             Log.e(logKey,"Error : $response")
                         }
                 }

                 override fun onFailure(call: Call<SearchResponseModel>, t: Throwable) {
                     Log.e(logKey,"Error Body : ${t.message.toString()}")
                 }

             })

        }
    }


