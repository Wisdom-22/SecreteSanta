package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://192.168.43.156/pythonprojects/"
class AppliancesActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var myApplianceAdapter: MyApplianceAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliances)

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
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<ApplianceListItem>?> {
            override fun onResponse(
                call: Call<List<ApplianceListItem>?>,
                response: Response<List<ApplianceListItem>?>
            ) {
                val responseBody = response.body()!!

                myApplianceAdapter = MyApplianceAdapter(baseContext, responseBody)
                myApplianceAdapter.notifyDataSetChanged()
                recyclerview.adapter = myApplianceAdapter

            }

            override fun onFailure(call: Call<List<ApplianceListItem>?>, t: Throwable) {
                d("Appliances", "onFailure " + t.message)
            }
        })
    }
}