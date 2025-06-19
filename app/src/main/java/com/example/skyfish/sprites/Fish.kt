package com.example.skyfish.sprites

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.example.skyfish.Config
import com.example.skyfish.R

class Fish {
    companion object {
        lateinit var bitmap: Bitmap
        val WIDTH = 0.3f
        val HEIGHT = 0.34f
        val LEFT = 0.1f
        val paint = Paint()
        val SPEED = 0.5f        // Доля экрана в секунду

        fun load(context: Context) {
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.fish)
        }
    }

    var y = 0f
    var heightScreen: Float? = null

    constructor(heightScreen: Float) {
        this.heightScreen = heightScreen
        y = heightScreen / 2f
    }

    fun GetRectF() : RectF {
        return RectF(heightScreen!! * LEFT,
            y - heightScreen!! * HEIGHT / 2f,
            LEFT + heightScreen!! * WIDTH,
            y + HEIGHT / 2f)
    }

    public fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, GetRectF(), paint)
    }

    public fun update(isUp: Boolean = false) {
        var newY = y
        if (isUp) {
            newY -= heightScreen!! * SPEED / Config.FPS
        } else {
            newY += heightScreen!! * SPEED / Config.FPS
        }
        if (newY - heightScreen!! * HEIGHT / 2f < 0 || newY + HEIGHT / 2f > heightScreen!!)
            return
        y = newY
    }
}