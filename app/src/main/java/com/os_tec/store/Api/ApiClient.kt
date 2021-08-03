package com.os_tec.store.Api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    companion object{
        private var retrofit: Retrofit? = null
        private var client = OkHttpClient.Builder()
        private val gson= GsonBuilder().setLenient().create()!!

        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Server.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build()
            }
            return retrofit
        }



    }
    }
