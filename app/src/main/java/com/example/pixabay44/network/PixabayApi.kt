package com.example.pixabay44.network

import com.example.pixabay44.model.PixabayModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    fun getImagesByWord(
        @Query("key")key:String="28914088-0fb59487549a00cfcac299658",
        @Query("q")keyWord:String,
        @Query("page")page:Int,
        @Query("per_page")perPage:Int=12
    ):Call<PixabayModel>

}