package com.batarilo.vinylcollection.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: RecordApiService by lazy {
        Retrofit.Builder()
            .baseUrl(RecordApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecordApiService::class.java)
    }
}