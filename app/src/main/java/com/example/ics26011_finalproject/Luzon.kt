package com.example.ics26011_finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import com.example.montesmp5.DatabaseHandler

class Luzon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.luzonpage)


        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val rm: List<RecipeModel> = databaseHandler.getRecipes("Luzon")
        val rmRecName = Array<String>(rm.size){"null"}
        var index = 0
        for(e in rm){
            rmRecName[index] = e.recName
            index++

        }
        val listview = findViewById<ListView>(R.id.luzonList)
        val myListAdapter = MyListAdapter(this, rmRecName)
        listview.adapter = myListAdapter

        val back = findViewById<ImageView>(R.id.backBtn)
        back.setOnClickListener {
            this.finish()
        }
    }

}