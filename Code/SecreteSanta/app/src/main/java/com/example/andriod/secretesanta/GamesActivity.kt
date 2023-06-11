package com.example.andriod.secretesanta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GamesActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var myGamesAdapter: MyGamesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

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
            .create(GamesInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<GamesListItem>?> {
            override fun onResponse(
                call: Call<List<GamesListItem>?>,
                response: Response<List<GamesListItem>?>
            ) {
                val responseBody = response.body()!!

                myGamesAdapter = MyGamesAdapter(baseContext, responseBody)
                myGamesAdapter.notifyDataSetChanged()
                recyclerview.adapter = myGamesAdapter

            }

            override fun onFailure(call: Call<List<GamesListItem>?>, t: Throwable) {
                d("GamesActivity", "onFailure " + t.message)
            }
        })
    }
}