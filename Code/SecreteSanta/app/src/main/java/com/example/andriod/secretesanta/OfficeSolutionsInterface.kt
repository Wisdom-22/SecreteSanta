package com.example.andriod.secretesanta

import retrofit2.Call
import retrofit2.http.GET

interface OfficeSolutionsInterface {

    @GET("mainOfficeSolutions" + ".php")
    fun getData(): Call<List<OfficeSolutionsListItem>>
}