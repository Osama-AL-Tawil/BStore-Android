package com.os_tec.store.Fragments.MyOrder

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Model.MyOrderResponseModel

class MyOrderViewModel:ViewModel() {
    val logKey="MyOrderViewModel"

fun getMyOrder(activity:Activity):MutableLiveData<MyOrderResponseModel>{
    return ApiRepository(activity).getMyOrder(logKey)

}
}