package com.os_tec.store.Fragments.Cart

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Model.CartResponseModel

class CartViewModel:ViewModel() {
    val logKey="CartViewModel.OS"

    fun getCart(activity: Activity):MutableLiveData<CartResponseModel>{
        return ApiRepository(activity).getCartData(logKey)
    }
}