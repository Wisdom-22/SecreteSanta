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

class HomeOutdoorsActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var myHomeOutdoorsAdapter: MyHomeOutdoorsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_outdoors)

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
            .create(HomeOutdoorsInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<HomeOutdoorsListItem>?> {
            override fun onResponse(
                call: Call<List<HomeOutdoorsListItem>?>,
                response: Response<List<HomeOutdoorsListItem>?>
            ) {
                val responseBody = response.body()!!

                myHomeOutdoorsAdapter = MyHomeOutdoorsAdapter(baseContext, responseBody)
                myHomeOutdoorsAdapter.notifyDataSetChanged()
                recyclerview.adapter = myHomeOutdoorsAdapter

            }
            override fun onFailure(call: Call<List<HomeOutdoorsListItem>?>, t: Throwable) {
                Log.d("HomeOutDoorsActivity", "onFailure " + t.message)
            }
        })
    }
}