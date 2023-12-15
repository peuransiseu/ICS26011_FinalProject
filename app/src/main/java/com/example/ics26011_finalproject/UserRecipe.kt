package com.example.ics26011_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.montesmp5.DatabaseHandler

class UserRecipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_recipes)

        val addRecipe  = findViewById<Button>(R.id.addRecipe)

        addRecipe.setOnClickListener {
            val intent = Intent(this, AddRecipe::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val rm: List<UserRecipeModel> = databaseHandler.getUserRecipes()
        val urmRecName = Array<String>(rm.size){"null"}
        var index = 0
        for(e in rm){
            urmRecName[index] = e.urName
            index++

        }
        val listview = findViewById<ListView>(R.id.userList)
        val myListAdapter = MyUserListAdapter(this, urmRecName)
        listview.adapter = myListAdapter
    }
}