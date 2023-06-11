package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HeaderActivity : AppCompatActivity() {
    private lateinit var username: TextView
    private lateinit var email: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_header)

        username = findViewById<TextView>(R.id.username)
        email = findViewById<TextView>(R.id.email)

        var user = intent.getStringExtra("username").toString()
        username.setText(user)


        displayData()
    }

    private fun displayData() {

    }
}