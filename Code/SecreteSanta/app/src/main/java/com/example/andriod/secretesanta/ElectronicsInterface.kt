package com.example.andriod.secretesanta

import retrofit2.Call
import retrofit2.http.GET

interface ElectronicsInterface {
    @GET("mainElectronics" + ".php")
    fun getData(): Call<List<ElectronicsListItem>>
}