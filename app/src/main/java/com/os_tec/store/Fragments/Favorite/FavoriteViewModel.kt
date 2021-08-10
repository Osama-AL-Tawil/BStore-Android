package com.os_tec.store.Fragments.Favorite

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Model.ProductsModel


class FavoriteViewModel:ViewModel() {
    val logKey="FavoriteViewModel.OS"

fun getFavorite(activity:Activity):MutableLiveData<ArrayList<ProductsModel>>{
    return ApiRepository(activity).getFavorite(logKey)
}


}