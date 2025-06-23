package com.example.skyfish.sprites

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.scale
import com.example.skyfish.Config
import com.example.skyfish.R

class Fish {
    companion object {
        lateinit var bitmap: Bitmap
        val WIDTH = 0.3f
        val HEIGHT = 0.34f
        val LEFT = 0.1f
        val paint = Paint()
        val SPEED = 0.45f        // Доля экрана в секунду

        fun load(context: Context) {
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.fish)
        }
    }

    val paint = Paint()
    var y = 0f
    var heightScreen: Float? = null

    constructor(heightScreen: Float) {
        this.heightScreen = heightScreen
        y = heightScreen / 2f
        bitmap = bitmap.scale(GetRectF().width().toInt(), GetRectF().height().toInt())
    }

    public fun GetRectF(): RectF {
        return RectF(
            heightScreen!! * LEFT,
            y - heightScreen!! * HEIGHT / 2f,
            LEFT + heightScreen!! * WIDTH,
            y + HEIGHT / 2f
        )
    }

    public fun GetCollisionRectF(): RectF {
        val r = GetRectF()
        r.set(
            r.left,
            r.top + r.height() * 0.2f,
            r.right,
            r.bottom - r.height() * 0.2f
        )
        return r
    }

    public fun draw(canvas: Canvas) {
        if (Config.SHOW_HITBOX)
            canvas.drawRect(GetCollisionRectF(), paint)
        canvas.drawBitmap(bitmap, GetRectF().left, GetRectF().top, null)
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