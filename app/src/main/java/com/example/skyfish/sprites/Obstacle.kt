package com.example.skyfish.sprites

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import com.example.skyfish.R

class Obstacle {
    companion object {
        lateinit var bitmaps: Array<Bitmap>
        lateinit var bitmapsRotated: Array<Bitmap>

        val WIDTH = 0.2f
        val paint = Paint()
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
    }

    fun GetRectF() : RectF {
        return RectF(x, y, x + heightScreen!! * WIDTH,
            y + h)
    }

    public fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, GetRectF(), paint)
    }

    public fun isOut() : Boolean {
        return false
    }

    public fun update(isUp: Boolean = false) {

    }
}