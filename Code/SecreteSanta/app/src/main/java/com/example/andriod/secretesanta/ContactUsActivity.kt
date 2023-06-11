package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ContactUsActivity : AppCompatActivity() {
    private lateinit var emailAddress: EditText
    private lateinit var subject: EditText
    private lateinit var message: EditText
    private lateinit var sendButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        //get reference to the views in the layout
        emailAddress = findViewById<EditText>(R.id.emailAddress)
        subject = findViewById<EditText>(R.id.subject)
        message = findViewById<EditText>(R.id.message)
        sendButton = findViewById<Button>(R.id.sendButton)


        sendButton.setOnClickListener (object: View.OnClickListener{
            override fun onClick(p0: View?) {
                //assign the text from the views to these variables
                val email = emailAddress.text.toString()
                val subject = subject.text.toString()
                val message = message.text.toString()

                //split the email if there are more tha one email addresses
                val addresses = email.split(",".toRegex()).toTypedArray()

                //use intent to send the information to the select email application
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, addresses)
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                    //if everything is ok, the next activity will be the selected email application
                if(intent.resolveActivity(packageManager) != null){
                   //start the activity
                   startActivity(intent)

                }else{
                  //display message if app is not installed
                        Toast.makeText(this@ContactUsActivity, "Required App is not installed", Toast.LENGTH_SHORT).show()
                }


            }
        })

    }
}