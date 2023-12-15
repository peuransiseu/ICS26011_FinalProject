package com.example.ics26011_finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.montesmp5.DatabaseHandler


class MainActivity : AppCompatActivity() {
    companion object {
        private var hasLoadedRecipes = false
        private lateinit var dbh: DatabaseHandler

        fun loadStaticRecipes() {
            if (!hasLoadedRecipes) {
                dbh.loadRecipes()
                hasLoadedRecipes = true
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepagesplash)
        dbh = DatabaseHandler(this)
        loadStaticRecipes()

        val loginBtn = findViewById<Button>(R.id.login1)
        val registerBtn = findViewById<Button>(R.id.createBtn)

        loginBtn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }



}