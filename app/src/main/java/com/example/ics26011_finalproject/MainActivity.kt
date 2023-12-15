package com.example.ics26011_finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.montesmp5.DatabaseHandler
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    var navigationView: NavigationView? = null
    var toolbar: Toolbar? = null
    var menu: Menu? = null
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)
        val dbh = DatabaseHandler(this)
        dbh.loadRecipes()

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

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        textView = findViewById(R.id.textView)
        toolbar = findViewById(R.id.toolbar)

    }



}