package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class RegisterActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var textViewLogin: TextView

    private lateinit var etFullname: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //php variables
        etFullname = findViewById<TextInputEditText>(R.id.etFullname)
        etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)
        btnRegister = findViewById<Button>(R.id.btnRegister)

        //removes action bar
        supportActionBar?.hide()


        btnRegister.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                var fullname: String
                var username: String
                var password: String
                var confirmPassword: String
                var email: String

                //assigns the values from those views  these variables
                fullname = etFullname.text.toString()
                username = etUsername.text.toString()
                password = etPassword.text.toString()
                confirmPassword = etConfirmPassword.text.toString()
                email = etEmail.text.toString()

                //makes sure that all the text fields are not empty
                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("") && !confirmPassword.equals("")
                    && password.equals(confirmPassword)) {

                    val handler = Handler(Looper.getMainLooper())
                    handler.post(Runnable {
                        val field = arrayOfNulls<String>(4)
                        field[0] = "fullname"
                        field[1] = "username"
                        field[2] = "password"
                        field[3] = "email"
                        //Creating array for data
                        val data = arrayOfNulls<String>(4)
                        data[0] = fullname
                        data[1] = username
                        data[2] = password
                        data[3] = email

                        //laptop should be connected to the inter using hotspot so that you will be able
                        //send data through the network and not worry about network restrictions enabled on a network
                        val putData = PutData(
                            "http://192.168.43.156/SecreteSanta/signup.php",
                            "POST",
                            field,
                            data
                        )
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                val result = putData.result

                                if(result.equals("Sign Up Success")){
                                    //I cannot use this "Toast.makeText(this, result, Toast.LENGTH_SHORT).show() " because it
                                    //gives an error
                                    //Displayed if everythin works fine
                                   Toast.makeText(this@RegisterActivity, result, Toast.LENGTH_SHORT).show()
                                  startLoginActivity()

                                }else{
                                    //Displayed if omething goes wrong
                                    Toast.makeText(this@RegisterActivity, result, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        //End Write and Read data with URL
                    })
                }else{
                    //Displayed if the user did not fill in one or all the text fields
                    Toast.makeText(this@RegisterActivity, "All fields are required", Toast.LENGTH_SHORT).show()
                }

            }
        })

        //reference to the login activity text
        textViewLogin = findViewById<Button>(R.id.textViewLogin)
        textViewLogin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                startLoginActivity()
                finish()
            }
        })

    }

    //method that takes the user to the login activity
    private fun startLoginActivity(){
        var intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}