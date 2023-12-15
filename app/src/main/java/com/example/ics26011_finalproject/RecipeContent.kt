package com.example.ics26011_finalproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class RecipeContent : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_page)

        val recipeName = findViewById<TextView>(R.id.tvRecipeName)
        val recipeImage = findViewById<ImageView>(R.id.tvRecipeImage)
        val recipeCookingTime = findViewById<TextView>(R.id.tvCookingTime)
        val recipeServingSize = findViewById<TextView>(R.id.tvServingSize)
        val recipeCalorieCount = findViewById<TextView>(R.id.tvCalorieCount)



    }

}