package com.tr1984.icebreak.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.tr1984.icebreak.model.Player
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
    var aCX = Float.MAX_VALUE
    var aCY = Float.MAX_VALUE
    var bCX = Float.MAX_VALUE
    var bCY = Float.MAX_VALUE

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        Logger.d(logTag, "PlayBoard initialized")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (right - left > 0 && aCX == Float.MAX_VALUE) {
            aCX = (width / 2).toFloat()
            bCX = aCX
            radius = (width / 30).toFloat()
            boundary.set(0, 0, width, height)
        }
        if (bottom - top > 0 && aCY == Float.MAX_VALUE) {
            aCY = (height / 2).toFloat()
            bCY = aCY
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
        }
        paint.color = Color.BLUE
        canvas?.drawCircle(bCX, bCY, radius, paint)
        paint.color = Color.RED
        canvas?.drawCircle(aCX, aCY, radius, paint)
    }

    fun update(player: Player) {
        if (player.uid == "a") {
            aCX -= player.x
            if (aCX <= radius) {
                aCX = radius
            }
            if (aCX >= boundary.right - radius) {
                aCX = boundary.right - radius
            }

            aCY += player.y
            if (aCY <= radius) {
                aCY = radius
            }
            if (aCY >= boundary.bottom - radius) {
                aCY = boundary.bottom - radius
            }
        } else {
            bCX -= player.x
            if (bCX <= radius) {
                bCX = radius
            }
            if (bCX >= boundary.right - radius) {
                bCX = boundary.right - radius
            }

            bCX += player.y
            if (bCX <= radius) {
                bCX = radius
            }
            if (bCX >= boundary.bottom - radius) {
                bCX = boundary.bottom - radius
            }
        }

        invalidate()
    }

    companion object {
        const val logTag = "PlayBoard"
    }
}