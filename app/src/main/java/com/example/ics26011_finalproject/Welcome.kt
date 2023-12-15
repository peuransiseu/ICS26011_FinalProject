package com.example.ics26011_finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class Welcome : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcomepage)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val userName = findViewById<TextView>(R.id.tvName)
        val luzon = findViewById<RelativeLayout>(R.id.luzonCard)
        val visayas = findViewById<RelativeLayout>(R.id.visayasCard)
        val mindanao = findViewById<RelativeLayout>(R.id.mindanaoCard)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_home -> {
                    Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_profile -> {
                    Toast.makeText(applicationContext, "Clicked Profile", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_liked_recipes -> {
                    Toast.makeText(applicationContext, "Thank You for Liking!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_own_recipes -> {
                    val i = Intent(this, UserRecipe::class.java)
                    startActivity(i)
                    Toast.makeText(applicationContext, "Clicked Own Recipes", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_share -> {
                    Toast.makeText(applicationContext, "Thank you for sharing!!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_rate -> {
                    Toast.makeText(applicationContext, "Thank you for rating us!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    Toast.makeText(applicationContext, "Successfully Logged Out", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }


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

    override fun onStart() {
        super.onStart()
        val userName = findViewById<TextView>(R.id.tvName)
        userName.text = intent.getStringExtra("fname").toString()+" "+intent.getStringExtra("lname").toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

