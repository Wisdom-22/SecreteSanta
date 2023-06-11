package com.example.andriod.secretesanta

import retrofit2.Call
import retrofit2.http.GET

interface GamesInterface {

    @GET("mainGame" + ".php")
    fun getData(): Call<List<GamesListItem>>
}