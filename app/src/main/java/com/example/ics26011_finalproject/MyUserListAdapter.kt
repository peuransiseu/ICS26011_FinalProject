package com.example.ics26011_finalproject

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyUserListAdapter(private val context: Activity, private val recipeName: Array<String>)
    : ArrayAdapter<String>(context, R.layout.listview_layout, recipeName) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.listview_layout, null, true)

        val nameText = rowView.findViewById<TextView>(R.id.recipeName)


        nameText.text = recipeName[position]

        return rowView


    }

}