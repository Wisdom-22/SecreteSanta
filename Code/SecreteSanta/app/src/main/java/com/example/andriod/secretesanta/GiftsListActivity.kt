package com.example.andriod.secretesanta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class GiftsListActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifts_list)

        //removes action bar
        supportActionBar?.hide()

        listView = findViewById<ListView>(R.id.listView)

        //these is the list of categories available
        val giftNames = arrayOf("Appliances", "Electronics", "Game", "Home OutDoors", "Office Solutions", "Extra")

        //get the adapter
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, giftNames)

        //attach the adapter to the list adapter
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener{ adapterView, view, i, l ->
            if(i == 0){
                startActivity(Intent(this@GiftsListActivity, AppliancesActivity::class.java))
                Toast.makeText(this, "Item Selected " + giftNames[i], Toast.LENGTH_SHORT).show()
            }else if(i ==1){
                startActivity(Intent(this@GiftsListActivity, ElectronicsActivity::class.java))
                Toast.makeText(this, "Item Selected " + giftNames[i], Toast.LENGTH_SHORT).show()
            }else if(i == 2){
                startActivity(Intent(this@GiftsListActivity, GamesActivity::class.java))
                Toast.makeText(this, "Item Selected " + giftNames[i], Toast.LENGTH_SHORT).show()
            }else if(i == 3){
                startActivity(Intent(this@GiftsListActivity, HomeOutdoorsActivity::class.java))
                Toast.makeText(this, "Item Selected " + giftNames[i], Toast.LENGTH_SHORT).show()
            }else if(i == 4){
                startActivity(Intent(this@GiftsListActivity, OfficeSolutionsActivity::class.java))
                Toast.makeText(this, "Item Selected " + giftNames[i], Toast.LENGTH_SHORT).show()
            }else if(i ==5){
                startActivity(Intent(this@GiftsListActivity, ElectronicsActivity::class.java))
                Toast.makeText(this, "Item Selected " + giftNames[i], Toast.LENGTH_SHORT).show()
            }
        }
    }
}