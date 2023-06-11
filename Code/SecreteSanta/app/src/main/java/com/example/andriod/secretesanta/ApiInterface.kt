package com.example.andriod.secretesanta

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("mainAppliance" + ".php")
    fun getData(): Call<List<ApplianceListItem>>
}