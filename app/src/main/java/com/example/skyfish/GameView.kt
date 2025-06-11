package com.example.skyfish

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


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

    private val rect_1 = RectF(1670f, 700f, 1920f, 1080f)
    private val rect_2 = RectF(1670f, 0f, 1920f, 400f)
    private val rect_fish = RectF(40f, 500f, 240f, 640f)
    private val paint = Paint()


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Рисуем прямоугольник или что угодно
        val let_1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.let_1);
        canvas.drawBitmap(let_1, null, rect_1, paint)
        val let_2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.let_2);
        canvas.drawBitmap(let_2, null, rect_2, paint)
        val fish = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish);
        canvas.drawBitmap(fish, null, rect_fish, paint)
    }

    fun updateRect(newRect: RectF) {
        rect_1.set(newRect)
        rect_2.set(newRect)
        rect_fish.set(newRect)
        invalidate()
    }
}