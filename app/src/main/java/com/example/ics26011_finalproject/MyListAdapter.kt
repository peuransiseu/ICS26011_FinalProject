package com.example.ics26011_finalproject

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val recipeName: Array<String>, private val recipeId: Array<String>, private val island:String)
    :ArrayAdapter<String>(context, R.layout.listview_layout, recipeName) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.listview_layout, null, true)

        val nameText = rowView.findViewById<TextView>(R.id.recipeName)
        val nameImg = rowView.findViewById<ImageView>(R.id.imageFood)

        val foodClick  = rowView.findViewById<LinearLayout>(R.id.foodClick)


        foodClick.setOnClickListener{
            val intent = Intent(context, Welcome::class.java)
            intent.putExtra("recipeName", recipeName)
            context.startActivity(intent)
        }

        nameText.text = recipeName[position]
        nameImg.setImageResource(context.getResources().getIdentifier(island+recipeId[position], "drawable", context.getPackageName()))

        return rowView


    }

}