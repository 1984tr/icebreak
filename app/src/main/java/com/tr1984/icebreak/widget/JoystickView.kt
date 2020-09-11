package com.tr1984.icebreak.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class JoystickView : View {

    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
    }

    private var center = PointF(0f, 0f)
    private var radius = 0f
    private var pointer = PointF(0f, 0f)
    private var pointerRadius = 0f

    private var oldX = 0f
    private var oldY = 0f

    var positionCallback: ((Float, Float) -> Unit)? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (width > 0 && radius <= 0) {
            center.x = (width / 2).toFloat()
            center.y = (height / 2).toFloat()
            radius = (width / 2).toFloat() - paint.strokeWidth / 2

            pointer.x = center.x
            pointer.y = center.y
            pointerRadius = radius / 3
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val e = event ?: return true
        val x = e.x
        val y = e.y
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                oldX = event.x
                oldY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                pointer.x += x - oldX
                pointer.y += y - oldY
                if (pointer.x <= pointerRadius) {
                    pointer.x = pointerRadius
                }
                if (pointer.x >= width - pointerRadius) {
                    pointer.x = width - pointerRadius
                }

                if (pointer.y <= pointerRadius) {
                    pointer.y = pointerRadius
                }
                if (pointer.y >= height - pointerRadius) {
                    pointer.y = height - pointerRadius
                }
                positionCallback?.invoke(-(x - oldX), y - oldY)
                oldX = x
                oldY = y
            }
            MotionEvent.ACTION_UP -> {
                pointer.x = center.x
                pointer.y = center.y
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val c = canvas ?: return

        with(paint) {
            color = Color.BLACK
            style = Paint.Style.STROKE
        }
        c.drawCircle(center.x, center.y, radius, paint)

        with(paint) {
            color = Color.LTGRAY
            style = Paint.Style.FILL_AND_STROKE
        }
        c.drawCircle(pointer.x, pointer.y, pointerRadius, paint)
    }
}