package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class LoginActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var textViewRegister: TextView
    private lateinit var forgotPassword: TextView

    private lateinit var etEmail: TextInputEditText //we can use the etEmail or etUsername view for login
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        textViewRegister = findViewById<TextView>(R.id.textViewRegister)
        forgotPassword = findViewById<TextView>(R.id.forgotPassword)

        /*btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                startHomeActivity()
                finish()
            }
        })*/

        //removes action bar
        supportActionBar?.hide()

        //when the button is pressed, it will send the users details to the server
        btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                var username: String
                var password: String
                var email: String

                //strings that will hold the text from both fields
                username = etUsername.text.toString()
                password = etPassword.text.toString()
                //email = etEmail.text.toString()

                //if((!username.equals("") || !email.equals("")) && !password.equals("")) {
                if(!username.equals("") && !password.equals("")) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post(Runnable {

                        //array that will hold the name of both information that will be sent to the server
                        val field = arrayOfNulls<String>(2)
                        //field[0] = "email"
                        field[0] = "username"
                        field[1] = "password"

                        //Creating array for data that will hold the actual text from both text input fields
                        val data = arrayOfNulls<String>(2)
                        //data[0] = email
                        data[0] = username
                        data[1] = password //this variable will be sent to the forgot password activity so that it can be used there

                        //laptop should be connected to the inter using hotspot so that you will be able
                        //send data through the network and not worry about network restrictions enabled on a network 
                        val putData = PutData(
                            "http://192.168.43.156/SecreteSanta/login.php",
                            "POST",
                            field,
                            data
                        )
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                val result = putData.result

                                if(result.equals("Login Success")){
                                    //I cannot use this "Toast.makeText(this, result, Toast.LENGTH_SHORT).show() " because it
                                    //gives an error
                                    //Displayed if everything works fine
                                    Toast.makeText(this@LoginActivity, result, Toast.LENGTH_SHORT).show()
                                    startHomeActivity(username) //sends the name to the other activity

                                }else{
                                    //displayed if there is an error
                                    Toast.makeText(this@LoginActivity, result, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    })
                }else{
                    //if both fields are not filled
                    Toast.makeText(this@LoginActivity, "All fields are required", Toast.LENGTH_SHORT).show()
                }

            }
        })


        //text view that will trigger the startRegisterActivity method
        textViewRegister.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                startRegisterActivity()
                finish()
            }
        })

        //text view that will trigger the startForgotPasswordActivity method
        forgotPassword.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                startForgotPasswordActivity()
            }
        })

    }



    //functions for the navigation to their respective views. Also sends user name to the home activity
    private fun startHomeActivity(username: String){
        var intent: Intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }

    //the startRegisterActivity method
    private fun startRegisterActivity(){
        var intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    //the startForgotPasswordActivity method
    private fun startForgotPasswordActivity(){
        var intent: Intent = Intent(this, ForgotActivity::class.java)
        startActivity(intent)

    }
}