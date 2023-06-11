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

class OfficeSolutionsActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var myOfficeSolutionsAdapter: MyOfficeSolutionsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_office_solutions)

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
            .create(OfficeSolutionsInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<OfficeSolutionsListItem>?> {
            override fun onResponse(
                call: Call<List<OfficeSolutionsListItem>?>,
                response: Response<List<OfficeSolutionsListItem>?>
            ) {
                val responseBody = response.body()!!

                myOfficeSolutionsAdapter = MyOfficeSolutionsAdapter(baseContext, responseBody)
                myOfficeSolutionsAdapter.notifyDataSetChanged()
                recyclerview.adapter = myOfficeSolutionsAdapter

            }

            override fun onFailure(call: Call<List<OfficeSolutionsListItem>?>, t: Throwable) {
                Log.d("OfficeSolutionsActivity", "onFailure " + t.message)
            }
        })
    }
}