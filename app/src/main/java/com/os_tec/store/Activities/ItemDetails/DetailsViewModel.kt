package com.os_tec.store.Activities.ItemDetails

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Model.DetailsResponseModel


class DetailsViewModel:ViewModel() {
    private val logKey="DetailsViewModel.OS"
    val bodyLiveData:MutableLiveData<DetailsResponseModel> = MutableLiveData()


      fun getItemDetails(activity:Activity,id: Int):MutableLiveData<DetailsResponseModel>{
        return ApiRepository(activity).getItemDetails(logKey, id)
}

//     fun getStock(activity:Activity,id: Int):MutableLiveData<Int>{
//        return ApiRepository(activity).getStock(logKey, id)
//     }




}