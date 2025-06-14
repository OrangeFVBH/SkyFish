package com.example.skyfish

import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var score: Long = 0
    var inc: Long = 1

    private lateinit var canva: View
    private lateinit var text_score: TextView

    private lateinit var shared: SharedPreferences
    private lateinit var sharedEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        shared = getSharedPreferences("main", 0)
        sharedEditor = shared.edit()
        load()

        text_score = findViewById(R.id.text_score)

        //TODO:canva.setOnClickListener()
    }



    private fun save(){
        sharedEditor.putLong("score", score)
        sharedEditor.commit()
    }

    private fun load(){
        score = shared.getLong("score", 0)
    }
}