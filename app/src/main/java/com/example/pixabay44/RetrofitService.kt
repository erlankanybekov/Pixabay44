package com.example.pixabay44

import com.example.pixabay44.network.PixabayApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
   // https://pixabay.com/api/?key=28914088-0fb59487549a00cfcac299658&q=yellow+flowers&image_type=photo

  private  val retrofit = Retrofit.Builder().baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()


    fun getApi():PixabayApi= retrofit.create(PixabayApi::class.java)
}