package com.example.andriod.secretesanta

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {
    private lateinit var btnLogout: Button
    private lateinit var getSecreteSanta: Button
    private lateinit var groupUser: Button
    //private lateinit var confirmRegistration: Button
    private lateinit var displaySecreteSanta: TextView
    private lateinit var displaySecreteSantaName: TextView
    private lateinit var displayUsername: TextView
    private lateinit var displayStatus: TextView
    var username: String = ""

    //Navigation part
    lateinit var toggle: ActionBarDrawerToggle

    //Bottom Navigation part
    private lateinit var bottomNavigation: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //get the reference from the layout file
        getSecreteSanta = findViewById<Button>(R.id.getSecreteSanta)
        displaySecreteSanta = findViewById<TextView>(R.id.displaySecreteSanta)
        displayUsername = findViewById<TextView>(R.id.displayUsername)
        displayStatus = findViewById<TextView>(R.id.displayStatus)
        displaySecreteSantaName = findViewById<TextView>(R.id.displaySecreteSantaName)

        //display username on the text view. This gets the user's name from where the home activity
        username = intent.getStringExtra("username").toString()
        username = username.toString()

        //display status of the user
        displayStatus()

        //called the method that will display the secrete santa if there is any.
        displaySecreteSanta()


        //method that triggers the php file that receives the secrete santa from the server. To get the Secrete Sanat
        //the library volley is used.
        getSecreteSanta.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                //val data: String = editText.text.toString() //*********i guess the name of the individual comes here
                //the volley library that will help receive the response from the server
                val queue = Volley.newRequestQueue(this@HomeActivity)
                val url = "http://192.168.43.156/SecreteSanta/getSecreteSanta.php"

                val stringRequest: StringRequest = object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        /*if(response.equals("Success")){
                            Toast.makeText(this@MainActivity, "Data added", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
                        }*/
                        try {
                            val jsonArray: JSONArray = JSONArray(response)
                            for(i in 0 until jsonArray.length()){
                                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                                //val id: String = jsonObject.getString("id") //for id
                                //val fullname: String = jsonObject.getString("fullname")
                                //val username: String = jsonObject.getString("username")

                                val secrete_santa: String = jsonObject.getString("secrete_santa")
                                displaySecreteSanta.setText("Secrete Santa: " + secrete_santa ) //textview that displays secrete santa
                            }
                        }catch(e: JSONException){
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener {
                        val error: VolleyError? = null
                        if (error != null) {
                            Log.e("Error", error.localizedMessage)
                        }
                    }) {
                    override fun getParams(): Map<String, String>? {
                        val paramV: MutableMap<String, String> = HashMap()
                        paramV.put("username", username)
                        return paramV
                    }
                }
                queue.add(stringRequest)
            }

        })





        //Navigation section
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this@HomeActivity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home ->{

                    startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
                }
                R.id.nav_settings -> Toast.makeText(this@HomeActivity, "Clicked Settings", Toast.LENGTH_SHORT).show()
                R.id.nav_group_users -> startGroupActivity(username)
                R.id.nav_confirm_registration -> confirmRegistration()
                R.id.nav_logout -> startLogOut()
                R.id.nav_contact_us -> startContactUsActivity()
                R.id.nav_rate_us -> Toast.makeText(this@HomeActivity, "Clicked Rate us", Toast.LENGTH_SHORT).show()


            }
            true
        }

        //This is the bottom navigation section
        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_gifts -> startActivity(Intent(this@HomeActivity, GiftsListActivity::class.java))

            }
            true
        }

    }

    //method that takes the user to the contact us activity
    private fun startContactUsActivity() {
        var intent: Intent = Intent(this, ContactUsActivity::class.java)
        startActivity(intent)
    }

    //function that will display the status of the current user
    private fun displayStatus(){
        val queue = Volley.newRequestQueue(this@HomeActivity)
        val url = "http://192.168.43.156/secretesanta/displayStatus.php"

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                /*if(response.equals("Success")){
                    Toast.makeText(this@HomeActivity, "Registered", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@HomeActivity, response, Toast.LENGTH_SHORT)
                        .show()
                }*/
                try {
                    val jsonArray: JSONArray = JSONArray(response)
                    for(i in 0 until jsonArray.length()){
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val status: String = jsonObject.getString("status")

                        displayUsername.setText("Username: " + username)
                        displayStatus.setText("Status: " + status)
                    }
                }catch(e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                val error: VolleyError? = null
                if (error != null) {
                    Log.e("Error", error.localizedMessage)
                }
            }) {
            override fun getParams(): Map<String, String>? {
                val paramV: MutableMap<String, String> = HashMap()

                paramV.put("username", username)
                return paramV
            }
        }
        queue.add(stringRequest)

    }

//the log out method
    private fun startLogOut(){
        var intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    //the group activity method
    private fun startGroupActivity(username: String){
        var intent: Intent = Intent(this, GroupUsersActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
//method that confirms registration of the user
    private fun confirmRegistration(){
        //this button helps to confirm registration
        //confirmRegistration.setOnClickListener(object: View.OnClickListener {
           // override fun onClick(p0: View?) {

                val queue = Volley.newRequestQueue(this@HomeActivity)
                val url = "http://192.168.43.156/secretesanta/registerUser.php"

                val stringRequest: StringRequest = object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        if(response.equals("Success")){
                            Toast.makeText(this@HomeActivity, "Registered", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this@HomeActivity, response, Toast.LENGTH_SHORT)
                                .show()
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

                        paramV.put("username", username)
                        paramV.put("status","Registered")
                        return paramV
                    }
                }
                queue.add(stringRequest)

           // }

       // })
    }

    //this method will get the secrete santa if it has been selected
    private fun displaySecreteSanta() {
        val queue = Volley.newRequestQueue(this@HomeActivity)
        val url = "http://192.168.43.156/secretesanta/displaySecreteSanta.php"

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->

                try {
                    val jsonArray: JSONArray = JSONArray(response)
                    for(i in 0 until jsonArray.length()){
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val secrete_santa: String = jsonObject.getString("secrete_santa")
                        //displays the secrete santa if there is any
                        displaySecreteSanta.setText("Secrete Santa: " + secrete_santa)
                    }
                }catch(e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                val error: VolleyError? = null
                if (error != null) {
                    Log.e("Error", error.localizedMessage)
                }
            }) {
            override fun getParams(): Map<String, String>? {
                val paramV: MutableMap<String, String> = HashMap()

                paramV.put("username", username)
                return paramV
            }
        }
        queue.add(stringRequest)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        if(toggle.onOptionsItemSelected(item)){

            return true
        }
        return super.onOptionsItemSelected(item)
        intent.putExtra(username, "username")
        startActivity(intent)
    }
}

