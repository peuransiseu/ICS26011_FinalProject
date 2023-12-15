package com.example.ics26011_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.montesmp5.DatabaseHandler
import com.example.montesmp5.EmpModelClass

class AddRecipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_recipe)

        val saveRecipe = findViewById<Button>(R.id.saveRecipeBtn)
        val recName = findViewById<EditText>(R.id.tvRecipeName)
        val recIngredients = findViewById<EditText>(R.id.tvRecipeName)
        val recServingSize = findViewById<EditText>(R.id.tvServeSize)
        val recCalories = findViewById<EditText>(R.id.tvCalories)
        val recTime = findViewById<EditText>(R.id.tvCookingTime)
        val recInstructions = findViewById<EditText>(R.id.tvInstructions)
        val recDescription = findViewById<EditText>(R.id.tvDescription)

        saveRecipe.setOnClickListener {

            val name = recName.text.toString()
            val ingred = recIngredients.text.toString()
            val size = recServingSize.text.toString()
            val cal = recCalories.text.toString()
            val time = recTime.text.toString()
            val ins = recInstructions.text.toString()
            val desc = recDescription.text.toString()

            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            val status =  databaseHandler.addRecipe(UserRecipeModel(name,ingred,size,cal,time,
                ins, desc))
            if(status > -1){
                Toast.makeText(this,"Recipe Saved", Toast.LENGTH_LONG).show()
                this.finish()
            }else{
                Toast.makeText(this,"Unable to Save Recipe, Please Try Again", Toast.LENGTH_LONG).show()
                recName.text.clear()
                recIngredients.text.clear()
                recServingSize.text.clear()
                recCalories.text.clear()
                recTime.text.clear()
                recInstructions.text.clear()
                recDescription.text.clear()
            }
        }
    }


}