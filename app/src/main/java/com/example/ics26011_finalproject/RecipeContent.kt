package com.example.ics26011_finalproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.montesmp5.DatabaseHandler

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
        val recipeIngredients = findViewById<TextView>(R.id.tv_ingredients_text)
        val recipeInstructions = findViewById<TextView>(R.id.tv_instructions_text)

        val back = findViewById<ImageView>(R.id.recipeBackBtn)
        back.setOnClickListener {
            this.finish()
        }

        val recipe = intent.getStringExtra("recipeName").toString()
        Log.d("test", recipe)

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val rm: List<RecipeModel> = databaseHandler.getClickedRecipe(recipe)
        val rmRecId = Array<String>(rm.size){"0"}
        val rmRecName = Array<String>(rm.size){"null"}
        val rmRecIsland = Array<String>(rm.size){"null"}
        val rmRecIngred = Array<String>(rm.size){"null"}
        val rmRecServe = Array<String>(rm.size){"null"}
        val rmRecCalories = Array<String>(rm.size){"null"}
        val rmRecTime = Array<String>(rm.size){"null"}
        val rmRecInstructions = Array<String>(rm.size){"null"}

        var index = 0
        for(e in rm){
            rmRecId[index] = e.recId.toString()
            rmRecName[index] = e.recName
            rmRecIsland[index] = e.recIsland
            rmRecIngred[index] = e.recIngred
            rmRecServe[index] = e.recServeSize
            rmRecCalories[index] = e.recCalories.toString()
            rmRecTime[index] = e.recTime
            rmRecInstructions[index] = e.recInstructions
            index++

        }

        recipeName.text = rmRecName[0]
        recipeImage.setImageResource(this.getResources().getIdentifier(rmRecIsland[0].toLowerCase()+rmRecId[0], "drawable", this.getPackageName()))

//        recipeImage. = rmRecName[0]

        recipeCookingTime.text = rmRecTime[0]
        recipeServingSize.text = rmRecServe[0]
        recipeIngredients.text = rmRecIngred[0]
        recipeCalorieCount.text = rmRecCalories[0]
        recipeInstructions.text = rmRecInstructions[0]
    }

}