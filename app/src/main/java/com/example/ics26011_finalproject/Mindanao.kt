package com.example.ics26011_finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import com.example.montesmp5.DatabaseHandler

class Mindanao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mindanaopage)

        val island = "Mindanao"
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val rm: List<RecipeModel> = databaseHandler.getRecipes(island)
        val rmRecId = Array<String>(rm.size){"0"}
        val rmRecName = Array<String>(rm.size){"null"}
        var index = 0
        for(e in rm){
            rmRecId[index] = e.recId.toString()
            rmRecName[index] = e.recName
            index++

        }
        val listview = findViewById<ListView>(R.id.mindanaoList)
        val myListAdapter = MyListAdapter(this, rmRecName, rmRecId,island.toLowerCase())
        listview.adapter = myListAdapter

        val back = findViewById<ImageView>(R.id.backBtn3)
        back.setOnClickListener {
            this.finish()
        }
    }
}