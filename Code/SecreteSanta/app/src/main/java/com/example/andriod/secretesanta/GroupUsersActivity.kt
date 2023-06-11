package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class GroupUsersActivity : AppCompatActivity() {
    private lateinit var etGroupname: TextInputEditText
    private lateinit var etUsersname: TextInputEditText
    private lateinit var etMessage: TextInputEditText
    private lateinit var createGroupButton: Button
    private lateinit var checkanswer: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_user)

        //getting the reference to those views in the activity
        etGroupname = findViewById<TextInputEditText>(R.id.etGroupname)
        etUsersname = findViewById<TextInputEditText>(R.id.etUsersname)
        etMessage = findViewById<TextInputEditText>(R.id.etMessage)
        createGroupButton = findViewById<Button>(R.id.createGroupButton)
        //checkanswer = findViewById<Button>(R.id.checkanswer)

        //the name of the person creating the video will have their name received here
        var creator: String? = intent.getStringExtra("username")
        //checkanswer.setText("Username: " + creator.toString())
        creator = creator.toString() //this username will be sent to the database

        //removes action bar
        supportActionBar?.hide()

        createGroupButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {

                //get the actual test sent from the views
                val etgroupname: String = etGroupname.text.toString()
                val etusersname: String = etUsersname.text.toString()
                val etmessage: String = etMessage.text.toString()

                //the library that wil send information to the server
                val queue = Volley.newRequestQueue(this@GroupUsersActivity)
                val url = "http://192.168.43.156/secretesanta/createGroup.php"


                val stringRequest: StringRequest = object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        if(response.equals("Success")){
                            //display if everything is a success
                            Toast.makeText(this@GroupUsersActivity, "Registered", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            //display if something happens
                            Toast.makeText(this@GroupUsersActivity, response, Toast.LENGTH_SHORT).show()
                        }
                        /*try {
                            val jsonArray: JSONArray = JSONArray(response)
                            for(i in 0 until jsonArray.length()){
                                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                                //val id: String = jsonObject.getString("id") //for id
                                //val data: String = jsonObject.getString("data")
                                //textView.setText("ID = " + id + " Data = " + data + "\n")
                                //textView.append("ID = " + id + " Data = " + data + "\n")
                                //Toast.makeText(this@MainActivity, data, Toast.LENGTH_SHORT).show()

                                val id: String = jsonObject.getString("id") //for id
                                val users_name: String = jsonObject.getString("users_name")
                                val secrete_santa_name: String = jsonObject.getString("secrete_santa_name")

                                textView.setText("ID = " + id + " Users Name = " + users_name + " Secrete Santa Name = " + secrete_santa_name)
                            }
                        }catch(e: JSONException){
                            e.printStackTrace()
                        }*/
                    },
                    Response.ErrorListener {
                        val error: VolleyError? = null
                        if (error != null) {
                            Log.e("Error", error.localizedMessage)
                        }
                    }) {
                    override fun getParams(): Map<String, String>? {
                        val paramV: MutableMap<String, String> = HashMap()

                        paramV.put("groupname",etgroupname)
                        paramV.put("usersname",etusersname)
                        paramV.put("creator",creator)
                        return paramV
                    }
                }
                queue.add(stringRequest)

            }

        })


    }
}