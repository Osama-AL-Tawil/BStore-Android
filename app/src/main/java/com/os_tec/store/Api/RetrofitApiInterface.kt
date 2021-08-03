package com.os_tec.store.Api

import com.google.gson.annotations.SerializedName
import com.os_tec.store.Model.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiInterface {

    //    @Headers("Content-Type:application/json")
    //    @Headers("Accept:application/json","Content-Type:application/json")


    @POST("register")
    fun signup(@Body  params: MutableMap<String, Any>): Call<RegistrationResponse>


    @POST("login")
    fun login(@Body params: HashMap<String, Any>): Call<RegistrationResponse>


    @POST("activate")
    fun activationCode(@Body params: HashMap<String, Any>): Call<RegistrationResponse>


    @GET("products/search")
    fun search(@Query("title", encoded = false) title: String): Call<SearchResponseModel>


    @GET("general/category")
    fun getCategories(): Call<CategoriesResponseModel>


    @GET("products/home")
    fun getHomeProducts(): Call<ProductsResponseModel>


    @GET("products/{id}/show")
    fun getProductDetails(@Path("id",encoded = false) id:Int): Call<DetailsResponseModel>


    @POST("products/favorite/store") //add is_favorite-> 1  remove is_favorite->0
    fun addAndRemoveFavorite(@Body  data:HashMap<String,Any>): Call<ProductsDataModel>


    @GET("products/favorite")
    fun getFavorite() : Call<ProductsDataModel>




}