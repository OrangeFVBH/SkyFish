package com.example.skyfish

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    public var score: Long = 0
        set(value) {
            field = value
            text_score?.text = value.toString()
        }

    private var text_score: TextView? = null
    public lateinit var fallMessage: View

    private lateinit var shared: SharedPreferences
    private lateinit var sharedEditor: SharedPreferences.Editor
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        shared = getSharedPreferences("main", 0)
        sharedEditor = shared.edit()
        load()

        text_score = findViewById(R.id.text_score)
        gameView = findViewById(R.id.gameView)
        fallMessage = findViewById(R.id.fallText)
        fallMessage.visibility = INVISIBLE
    }



    private fun save(){
        sharedEditor.putLong("score", score)
        sharedEditor.commit()
    }

    private fun load(){
        score = shared.getLong("score", 0)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            gameView.isTouch = true
        } else if (event?.action == MotionEvent.ACTION_UP) {
            gameView.isTouch = false
        }
        return super.onTouchEvent(event)
    }

    public fun ShowFallMessge() {
        val fallView = findViewById<TextView>(R.id.fallText)
        fallView.visibility = VISIBLE;
    }
}