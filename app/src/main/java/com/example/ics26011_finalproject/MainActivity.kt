package com.example.ics26011_finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart(){
        super.onStart()

        Toast.makeText(this, "In onStart", Toast.LENGTH_SHORT).show();

        Log.i("info", "In onStart")
    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(this, "In onResume", Toast.LENGTH_SHORT).show()

        Log.i("info", "In onResume")
    }

    override fun onPause() {
        super.onPause()

        Toast.makeText(this, "In onPause", Toast.LENGTH_SHORT).show()

        Log.i("info", "In onPause")
    }

    override fun onStop() {
        super.onStop()

        Toast.makeText(this, "In onStop", Toast.LENGTH_SHORT).show()

        Log.i("info", "In onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "In onDestroy", Toast.LENGTH_SHORT).show()

        Log.i("info", "In onDestroy")
    }


}