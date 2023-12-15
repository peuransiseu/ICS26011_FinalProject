package com.example.ics26011_finalproject

import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.montesmp5.DatabaseHandler

class Luzon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.luzonpage)

        val island = "Luzon"
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val rm: List<RecipeModel> = databaseHandler.getRecipes(island)
        val rmRecId = Array<String>(rm.size){"0"}
        val rmRecName = Array<String>(rm.size){"null"}
        var index = 0



        for(e in rm){
            rmRecId[index] = e.recId.toString()
            rmRecName[index] = e.recName
            index++
        }

        val listview = findViewById<ListView>(R.id.luzonList)
        val myListAdapter = MyListAdapter(this, rmRecName, rmRecId,island.toLowerCase())
        listview.adapter = myListAdapter

        val back = findViewById<ImageView>(R.id.backBtn)
        back.setOnClickListener {
            this.finish()
        }

        listview.setOnItemClickListener { parent, name, id, island ->
            Toast.makeText(this, "Clicked: $name", Toast.LENGTH_SHORT).show()
//            val element = myListAdapter.getItem(id) // The item that was clicked
//            Toast.makeText(this, "Clicked: $element", Toast.LENGTH_SHORT).show()
        }



//        val listView = findLayoutById(R.layout.listview_layout)
//        val openRecipe = listView.findViewById<LinearLayout>(R.id.foodClick)
//
//        openRecipe.setOnClickListener{
//
//            findViewById<TextView>(R.id.recipeName)
//            val intent = Intent(this, Welcome::class.java)
//            intent.putExtra("fname",e.userFirstName)
//            intent.putExtra("lname",e.userLastName)
//            startActivity(intent)
//            return
//        }



    }


}


