package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class ForgotActivity : AppCompatActivity() {
    private lateinit var btnConfirmPassword: Button
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        //Get the reference to each view
        etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)

        //removes action bar
        supportActionBar?.hide()

        //gets the reference of the button from the view
        btnConfirmPassword = findViewById<Button>(R.id.btnConfirmPassword)
        btnConfirmPassword.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var username: String
                var password: String
                var confirmPassword: String

                //assigns the strings from the views to these variables
                username = etUsername.text.toString()
                password = etPassword.text.toString()
                confirmPassword = etConfirmPassword.text.toString()

                //make sure that each field is not empty
                if (!username.equals("") && !password.equals("") && !confirmPassword.equals("") && password.equals(confirmPassword)) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post(Runnable {

                        val field = arrayOfNulls<String>(3)
                        //the name of each field
                        field[0] = "username"
                        field[1] = "password"
                        field[2] = "confirmPassword"

                        //Creating array for data
                        val data = arrayOfNulls<String>(3)
                        //assigning the actual text to each position in the array
                        data[0] = username
                        data[1] = password
                        data[2] = confirmPassword

                        //laptop should be connected to the inter using hotspot so that you will be able
                        //send data through the network and not worry about network restrictions enabled on a network
                        val putData = PutData(
                            "http://192.168.43.156/SecreteSanta/forgotPassword.php",
                            "POST",
                            field,
                            data
                        )

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                val result = putData.result //result comes from the php file

                                //displays if everything is ok
                                if (result.equals("Password Update Success")) {
                                    //I cannot use this "Toast.makeText(this, result, Toast.LENGTH_SHORT).show() " because it
                                    //gives an error
                                    Toast.makeText(this@ForgotActivity, result, Toast.LENGTH_SHORT).show()
                                    startLoginActivity()

                                } else {
                                    Toast.makeText(this@ForgotActivity, result, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    })
                }else{
                    //displayed of one or all the fields are empty
                    Toast.makeText(this@ForgotActivity, "All fields must be the same and filled", Toast.LENGTH_SHORT).show()
                }

            }
        })


    }
    //Takes the user to the a login activity
    private fun startLoginActivity() {
        var intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}