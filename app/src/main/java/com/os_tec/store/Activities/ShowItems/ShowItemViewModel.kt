package com.os_tec.store.Activities.ShowItems

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Model.ProductsModel


class ShowItemViewModel:ViewModel() {

    fun getCategoryItem(activity:Activity,categoryId:Int):MutableLiveData<ArrayList<ProductsModel>>{
            val params: MutableMap<String, Any> = HashMap()
            params["category_id"] = categoryId

            return ApiRepository(activity)
                .searchAndCategory("ShowItemViewModel", params,true)//execute Function ->return data

    }
}