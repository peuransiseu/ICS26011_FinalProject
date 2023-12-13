package com.example.ics26011_finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.montesmp5.DatabaseHandler

class Luzon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.luzonpage)
//
//        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
//        val emp: List<RecipeModel> = databaseHandler.viewEmployee()
//        val empArrayId = Array<String>(emp.size){"0"}
//        val empArrayName = Array<String>(emp.size){"null"}
//        val empArrayEmail = Array<String>(emp.size){"null"}
//        var index = 0
//        for(e in emp){
//            empArrayId[index] = e.userId.toString()
//            empArrayName[index] = e.userName
//            empArrayEmail[index] = e.userEmail
//            index++
//
//        }
//        val listview = findViewById<ListView>(R.id.listView)
//        val myListAdapter = MyListAdapter(this, empArrayId, empArrayName, empArrayEmail)
//        listview.adapter = myListAdapter
    }
}