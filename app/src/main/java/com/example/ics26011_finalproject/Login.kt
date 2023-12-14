package com.example.ics26011_finalproject

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.montesmp5.DatabaseHandler
import com.example.montesmp5.EmpModelClass

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)
    }

    fun login(view: View){
        val databaseHandler = DatabaseHandler(this)
        val emp: List<EmpModelClass> = databaseHandler.getUser()

        val user = findViewById<EditText>(R.id.loginUname).text.toString()
        val pass = findViewById<EditText>(R.id.loginPword).text.toString()

        for(e in emp){
            if(user.equals(e.userUser) && pass.equals(e.userPass)){
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this, Welcome::class.java)
                intent.putExtra("fname",e.userFirstName)
                intent.putExtra("lname",e.userLastName)
                startActivity(intent)
                return
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Incorrect Username/Password.")
            .setMessage("Please Try Again!")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()


    }

}