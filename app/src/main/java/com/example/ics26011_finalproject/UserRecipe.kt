package com.example.ics26011_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
}