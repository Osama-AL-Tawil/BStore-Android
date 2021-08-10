package com.os_tec.store.Fragments.Search

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Model.ProductsModel

class SearchViewModel:ViewModel() {
    val logKey="SearchViewModel.OS"


    fun search(activity: Activity, title: String):MutableLiveData<ArrayList<ProductsModel>> {
        val params: MutableMap<String, Any> = HashMap()
        params["title"] = title

        return ApiRepository(activity).searchAndCategory(logKey, params,false)//execute Function -> return data(MutableLiveData)
    }
}


