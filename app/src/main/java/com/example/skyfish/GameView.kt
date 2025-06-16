package com.example.skyfish

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import com.example.skyfish.sprites.Fish
import com.example.skyfish.sprites.Obstacle
import com.example.skyfish.sprites.ObstaclePosition
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameView : View {
    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        val h = displayMetrics.heightPixels.toFloat()

        Fish.load(context)
        Obstacle.load(context)
        fish = Fish(h)
        obstacles.addLast(Obstacle(
            h = 380f,
            x = 1670f,
            position = ObstaclePosition.UP,
            heightScreen = h
        ))
        obstacles.addLast(Obstacle(
            h = 380f,
            x = 1670f,
            position = ObstaclePosition.DOWN,
            heightScreen = h
        ))


        jobFPS = MainScope().launch {
            while(true) {
                updateFrame()
                delay(1000L / Config.FPS)
            }
        }
    }

    private val paint = Paint()
    private lateinit var fish: Fish
    private val obstacles = ArrayDeque<Obstacle>()

    private var jobFPS: Job? = null
    public var isTouch = false


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (obs in obstacles) {
            obs.draw(canvas)
        }
        fish.draw(canvas)
    }

    fun updateFrame() {
        fish.update(isTouch)
        while (obstacles.isNotEmpty() && obstacles.first().isOut()) {
            obstacles.removeFirst()
        }
        for (obs in obstacles) {
            obs.update()
        }
        invalidate()
    }
}