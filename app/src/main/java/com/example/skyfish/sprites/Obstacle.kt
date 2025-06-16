package com.example.skyfish.sprites

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.example.skyfish.Config
import com.example.skyfish.R
import kotlin.random.Random

class Obstacle {
    companion object {
        lateinit var bitmaps: Array<Bitmap>

        val WIDTH = 150f
        val paint = Paint()
        val random = java.util.Random()

        fun load(context: Context) {
            bitmaps = arrayOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.let_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.let_2),
            )
        }
    }

    var y = 0f
    var h = 0f
    var x = 0f
    var numberBitmap = 0
    var heightScreen: Float? = null

    constructor(
        heightScreen: Float,
        x: Float,
        position: ObstaclePosition,
        h: Float
    ) {
        this.heightScreen = heightScreen
        this.x = x
        numberBitmap = random.nextInt(bitmaps.size)
        this.h = h
        if (position == ObstaclePosition.DOWN) {
            y = heightScreen - h
        }
    }

    fun GetRectF() : RectF {
        return RectF(x, y, x + WIDTH, y + h)
    }

    public fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmaps[numberBitmap], null, GetRectF(), paint)
    }

    public fun isOut() : Boolean {
        return false
    }

    public fun update(isUp: Boolean = false) {

    }
}