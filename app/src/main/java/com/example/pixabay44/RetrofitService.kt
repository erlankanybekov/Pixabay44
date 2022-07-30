package com.example.pixabay44

import com.example.pixabay44.network.PixabayApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

  private  val retrofit = Retrofit.Builder().baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()


    fun getApi():PixabayApi= retrofit.create(PixabayApi::class.java)
}