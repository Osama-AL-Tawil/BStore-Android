package com.os_tec.store.Api

import com.google.gson.annotations.SerializedName
import com.os_tec.store.Model.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApiInterface {

    //    @Headers("Content-Type:application/json")
    //    @Headers("Accept:application/json","Content-Type:application/json")


    @POST("register")
    fun signup(@Body params: MutableMap<String, Any>): Call<RegistrationResponse>

    @POST("login")
    fun login(@Body params: HashMap<String, Any>): Call<RegistrationResponse>

    @POST("logout")
    fun logout(): Call<RegistrationResponse>

    @POST("activate")
    fun activationCode(@Body params: HashMap<String, Any>): Call<RegistrationResponse>

    @POST("password/forget") //params -> email
    fun forgetPassword(@Body params: HashMap<String, Any>): Call<RegistrationResponse>

    @PATCH("password") //params -> old_password , password , password_confirmation
    fun resetPassword(@Body params: HashMap<String, Any>): Call<RegistrationResponse>


    @GET("slider")
    fun getSlider(): Call<SliderResponseModel>

    @GET("products/search")
    fun searchAndCategory(@QueryMap(encoded = false) params: MutableMap<String, Any>): Call<SearchResponseModel>

    @GET("general/category")
    fun getCategories(): Call<CategoriesResponseModel>


    @GET("products/home")
    fun getHomeData(): Call<ProductsResponseModel>


    @GET("products/{id}/show")
    fun getProductDetails(@Path("id",encoded = false) id:Int): Call<DetailsResponseModel>



     //Favorite..........................
    @POST("products/favorite/store") //add is_favorite-> 1  remove is_favorite->0
     fun addAndRemoveFavorite(@Body  data:HashMap<String,Any>): Call<ResponseModel>

    @GET("products/favorite")
    fun getFavorite() : Call<FavoriteResponseModel>



    //Cart----------------------
    @GET("products/cart")
    fun getCart() : Call<CartResponseModel>

    @POST("products/cart/store")
    fun addToCart(@Body params: HashMap<String, Any>) : Call<ResponseModel>

    @DELETE("products/cart/{id}")
    fun deleteFromCart(@Path("id", encoded = false) id: Int): Call<ResponseModel>

    @PUT("products/cart") //params -> cart_id , quantity
    fun updateCart(@Body params: HashMap<String, Any>): Call<ResponseModel>



    //Payment-----------------------------------
    @POST("payment")
    fun payment(@Body params: HashMap<String, Any>): Call<ResponseModel>

    @GET("payment/my-orders")
    fun getMyOrder(): Call<MyOrderResponseModel>

    @POST("products/buy")
    fun buy(@Body params: HashMap<String, Any>): Call<ResponseModel>



}