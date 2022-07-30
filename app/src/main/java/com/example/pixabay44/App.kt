package com.example.pixabay44

import android.app.Application
import com.example.pixabay44.network.PixabayApi
import retrofit2.Retrofit

class App:Application() {

    companion object{
        lateinit var api:PixabayApi
    }

    override fun onCreate() {
        super.onCreate()
        val retrofitService = RetrofitService()
        api =retrofitService.getApi()

    }
}