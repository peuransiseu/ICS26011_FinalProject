package com.example.ics26011_finalproject

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.montesmp5.DatabaseHandler
import com.example.montesmp5.EmpModelClass

class Register : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registerpage)

        val u_email = findViewById<EditText>(R.id.registerEmail)
        val u_fname = findViewById<EditText>(R.id.registerFname)
        val u_lname = findViewById<EditText>(R.id.registerLName)
        val u_user = findViewById<EditText>(R.id.registerUname)
        val u_pass = findViewById<EditText>(R.id.registerPword)
        val register = findViewById<Button>(R.id.registerBtn)
        val loginHere = findViewById<TextView>(R.id.loginHere1)

        loginHere.setOnClickListener {
            val i = Intent(this, Login::class.java)
            startActivity(i)
        }

        register.setOnClickListener {
            val email = u_email.text.toString()
            val fname = u_fname.text.toString()
            val lname = u_lname.text.toString()
            val user = u_user.text.toString()
            val pass = u_pass.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            //simple validation
            if(email.trim()!=""&&fname.trim()!=""&&lname.trim()!=""&&user.trim()!=""&&pass.trim()!=""){

                if (!pass.contains("[0-9]".toRegex()) || !pass.contains("[A-Z]".toRegex()) || !pass.contains("[a-z]".toRegex()) || !pass.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())
                    || pass.length<8 ) {
                    u_pass.text.clear()

                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder
                        .setTitle("Invalid Password!")
                        .setMessage("Password must contain: \n\tAtleast 8 characters" +
                                "\n\tAn Uppercase Letter\n\tA Lowercase Letter\n\tA Mumber\n\tA Special Character")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                }else{
                    val status =  databaseHandler.addUser(EmpModelClass(email,fname,lname,user,pass))
                    if(status > -1){
                        Toast.makeText(this,"Record Saved", Toast.LENGTH_LONG).show()
                        this.finish()
                    }
                }

            }else {
                Toast.makeText(this, "Information cannot be blank", Toast.LENGTH_LONG)
                    .show()
                u_email.text.clear()
                u_fname.text.clear()
                u_lname.text.clear()
                u_user.text.clear()
                u_pass.text.clear()
            }
        }
    }
}