package com.tr1984.icebreak.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.tr1984.icebreak.util.Logger

class PlayView : View {

    private val paint by lazy {
        Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
    }

    private var boundary = Rect()
    private var radius = 0f
    private var cX = Float.MAX_VALUE
    private var cY = Float.MAX_VALUE

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        Logger.d(logTag, "PlayBoard initialized")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (right - left > 0 && cX == Float.MAX_VALUE) {
            cX = (width / 2).toFloat()
            radius = (width / 30).toFloat()
            boundary.set(0, 0, width, height)
        }
        if (bottom - top > 0 && cY == Float.MAX_VALUE) {
            cY = (height / 2).toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        with(paint) {
            style = Paint.Style.STROKE
            color = Color.RED
        }
        canvas?.drawRect(boundary, paint)

        with(paint) {
            style = Paint.Style.FILL
            color = Color.BLUE
        }
        canvas?.drawCircle(cX, cY, radius, paint)
    }

    fun update(accX: Float, accY: Float) {
        cX -= accX
        if (cX <= radius) {
            cX = radius
        }
        if (cX >= boundary.right - radius) {
            cX = boundary.right - radius
        }

        cY += accY
        if (cY <= radius) {
            cY = radius
        }
        if (cY >= boundary.bottom - radius) {
            cY = boundary.bottom - radius
        }
        invalidate()
    }

    companion object {
        const val logTag = "PlayBoard"
    }
}