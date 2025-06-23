package com.example.skyfish

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.icu.util.Calendar
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.skyfish.sprites.Fish
import com.example.skyfish.sprites.Obstacle
import com.example.skyfish.sprites.ObstaclePosition
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random


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

    }

    private val random = Random()
    private lateinit var fish: Fish
    private val obstacles = ArrayDeque<Obstacle>()

    private var jobFPS: Job? = null
    var isTouch = false
    private var lastObstacleTime: Long = 0;

    private val OBSTACLE_DELAY: Long = 1500;

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Fish.load(context)
        Obstacle.load(context)
        fish = Fish(height.toFloat())
        jobFPS = MainScope().launch {
            while(true) {
                if (checkCollision())
                    fall()
                generateObstacles()
                updateFrame()
                delay(1000L / Config.FPS)
            }
        }
    }

    private fun checkCollision(): Boolean {
        if (obstacles.isEmpty())
            return false
        return fish.GetCollisionRectF().intersect(obstacles.first().GetCollisionRectF())
    }

    private fun generateObstacles() {
        val curTime: Long = Calendar.getInstance().timeInMillis
        val h = height.toFloat()
        if ((curTime - lastObstacleTime) >= OBSTACLE_DELAY) {
            obstacles.addLast(Obstacle(
                h = (0.4f + 0.2f * random.nextFloat()) * h,
                x = width.toFloat(),
                position = if (random.nextBoolean())
                    ObstaclePosition.UP
                else
                    ObstaclePosition.DOWN,
                heightScreen = h
            ))
            lastObstacleTime = curTime
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (obs in obstacles) {
            obs.draw(canvas)
        }
        fish.draw(canvas)
    }

    private fun updateFrame() {
        fish.update(isTouch)
        while (obstacles.isNotEmpty() && obstacles.first().isOut()) {
            obstacles.removeFirst()
            Log.i("Obstacle", "Obstacle removed")
            (context as MainActivity).score++
        }
        for (obs in obstacles) {
            obs.update()
        }
        invalidate()
    }

    private fun fall() {
        jobFPS?.cancel()
        (context as MainActivity).fallMessage.visibility = VISIBLE
    }
}