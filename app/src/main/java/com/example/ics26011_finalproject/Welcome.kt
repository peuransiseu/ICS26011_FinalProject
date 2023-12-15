package com.example.ics26011_finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcomepage)

        val userName = findViewById<TextView>(R.id.tvName)
        val luzon = findViewById<RelativeLayout>(R.id.luzonCard)
        val visayas = findViewById<RelativeLayout>(R.id.visayasCard)
        val mindanao = findViewById<RelativeLayout>(R.id.mindanaoCard)

        userName.text = intent.getStringExtra("fname").toString()+" "+intent.getStringExtra("lname").toString()

        luzon.setOnClickListener {
            val intent = Intent(this, Luzon::class.java)
            startActivity(intent)
        }

        visayas.setOnClickListener {
            val intent = Intent(this, Visayas::class.java)
            startActivity(intent)
        }

        mindanao.setOnClickListener {
            val intent = Intent(this, Mindanao::class.java)
            startActivity(intent)
        }
    }
}