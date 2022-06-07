package com.batarilo.vinylcollectionPremium.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private var okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
    val API: DiscogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(DiscogApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DiscogApiService::class.java)
    }
}