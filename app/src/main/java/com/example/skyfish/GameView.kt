package com.example.skyfish

import android.content.Context
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

    private val paint = Paint()
    private val rect = RectF(100f, 100f, 150f, 250f)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Рисуем прямоугольник или что угодно
        canvas.drawRect(rect, paint)
    }

    fun updateRect(newRect: RectF) {
        rect.set(newRect)
        invalidate()
    }
}