package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //gets the reference of the two buttons
        btnLogin = findViewById<Button>(R.id.btnLogin)
        btnRegister = findViewById<Button>(R.id.btnRegister)

        //use the reference to create an event that will trigger the startLoginActivity method
        btnLogin.setOnClickListener (object: View.OnClickListener{
            override fun onClick(p0: View?) {
                startLoginActivity()
                finish()//the finish method removes all the other activities in the activity life cycle so that the
                        // application does not go through all the activities
            }
        })

        //use the reference to create an event that will trigger the startRegisterActivity method
        btnRegister.setOnClickListener (object: View.OnClickListener{
            override fun onClick(p0: View?) {
                startRegisterActivity()
                finish()//the finish method removes all the other activities in the activity life cycle so that the
                // application does not go through all the activities
            }
        })

        supportActionBar?.hide() //removes the action bar
    }

    //intent for login activity
    private fun startLoginActivity(){
        var intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    //intent for register activity
    private fun startRegisterActivity(){
        var intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}