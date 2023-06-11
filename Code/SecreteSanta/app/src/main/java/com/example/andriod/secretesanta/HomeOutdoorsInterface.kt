package com.example.andriod.secretesanta

import retrofit2.Call
import retrofit2.http.GET

interface HomeOutdoorsInterface {

    @GET("mainHomeOutdoors" + ".php")
    fun getData(): Call<List<HomeOutdoorsListItem>>
}