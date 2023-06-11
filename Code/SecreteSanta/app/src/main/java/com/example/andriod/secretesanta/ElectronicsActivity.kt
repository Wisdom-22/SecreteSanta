package com.example.andriod.secretesanta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ElectronicsActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var myElectronicsAdapter: MyElectronicsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electronics)

        recyclerview = findViewById(R.id.recyclerview)

        recyclerview.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ElectronicsInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<ElectronicsListItem>?> {
            override fun onResponse(
                call: Call<List<ElectronicsListItem>?>,
                response: Response<List<ElectronicsListItem>?>
            ) {
                val responseBody = response.body()!!

                myElectronicsAdapter = MyElectronicsAdapter(baseContext, responseBody)
                myElectronicsAdapter.notifyDataSetChanged()
                recyclerview.adapter = myElectronicsAdapter

            }

            override fun onFailure(call: Call<List<ElectronicsListItem>?>, t: Throwable) {
                Log.d("Electronics", "onFailure " + t.message)
            }
        })
    }
}