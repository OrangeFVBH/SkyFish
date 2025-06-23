package com.example.skyfish.sprites

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.scale
import com.example.skyfish.Config
import com.example.skyfish.R

class Obstacle {
    companion object {
        val paint = Paint()
        lateinit var bitmaps: Array<Bitmap>
        lateinit var bitmapsRotated: Array<Bitmap>

        val random = java.util.Random()

        fun load(context: Context) {
            bitmaps = arrayOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.let_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.let_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.let_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.let_4),
                BitmapFactory.decodeResource(context.resources, R.drawable.let_5),
            )
            val matrix = Matrix()
            matrix.postRotate(180f)
            bitmapsRotated = bitmaps.map {
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, false)
            }.toTypedArray()
        }
    }

    var y = 0f
    var h = 0f
    var x = 0f
    var bitmap: Bitmap
    var heightScreen: Float? = null
    var WIDTH: Float = 0f
        get() = 0.2f * heightScreen!!
    var SPEED: Float = 0f
        get() = 0.5f * heightScreen!! / Config.FPS

    constructor(
        heightScreen: Float,
        x: Float,
        position: ObstaclePosition,
        h: Float
    ) {
        this.heightScreen = heightScreen
        this.x = x
        val n = random.nextInt(bitmaps.size);
        this.h = h
        if (position == ObstaclePosition.DOWN) {
            y = heightScreen - h
            bitmap = bitmapsRotated[n]
        } else {
            bitmap = bitmaps[n]
        }
        bitmap = bitmap.scale(GetRectF().width().toInt(), GetRectF().height().toInt())
    }

    fun GetRectF() : RectF {
        return RectF(x, y, x + WIDTH, y + h)
    }

    public fun GetCollisionRectF() : RectF {
        val r = GetRectF()
        r.set(
            r.left + r.width() * 0.3f,
            r.top,
            r.right - r.width() * 0.3f,
            r.bottom
        )
        return r
    }

    public fun draw(canvas: Canvas) {
        if (Config.SHOW_HITBOX)
            canvas.drawRect(GetCollisionRectF(), paint)
        canvas.drawBitmap(bitmap, GetRectF().left, GetRectF().top, null)
    }

    public fun isOut() : Boolean {
        return x < -WIDTH
    }

    public fun update(isUp: Boolean = false) {
        x -= SPEED
    }
}