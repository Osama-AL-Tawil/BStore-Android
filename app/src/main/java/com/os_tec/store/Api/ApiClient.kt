package com.os_tec.store.Api

import android.content.Context
import com.os_tec.store.Classes.MyApp
import com.os_tec.store.Classes.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        private const val TAG = "ApiClient"
        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(): Retrofit {
            val sharedPreferences=SharedPreferences()

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient= OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
            httpClient.addInterceptor(logging)


            if (sharedPreferences.authorization()) { //if the user !=null -> login

                httpClient.addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "application/json")
                            //set user token from shared preferences
                        .header("Authorization", "Bearer " + sharedPreferences.getUserData().data.api_token)
                        //  .header("Authorization", "Bearer " + "29|xWh5qRtnZiDI12jJBzGFcuCdrnVa6bZ9KcQw8tIy")
                        .method(original.method, original.body)
                        .build()
                    chain.proceed(request)
                }
                )

            //if user==null
            } else {
                httpClient.addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "application/json")
                        .method(original.method, original.body)
                        .build()
                    chain.proceed(request)

                }
                )
            }



            //build retrofit
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Server.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit!!

        }



    }

}
