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

    private val stdWidth = 1000
    private val stdRadius = 50
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
        textSize = 30f
    }

    private var boundary = Rect()
    private var radius = 0f
    private var cX = 0f
    private var cY = 0f
    private var players = hashMapOf<String, Player>()

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        Logger.d(logTag, "PlayBoard initialized")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (width > 0 && radius <= 0) {
            radius = (width / (stdWidth / stdRadius)).toFloat()
            cX = (width / 2).toFloat()
            cY = (height / 2).toFloat()
            boundary.set(0, 0, width, height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val c = canvas ?: return

        with(paint) {
            style = Paint.Style.STROKE
            color = Color.BLACK
        }
        c.drawRect(boundary, paint)

        paint.style = Paint.Style.FILL
        players.forEach {
            val player = it.value
            paint.color = Color.parseColor(player.color)
            c.drawCircle(player.x, player.y, radius, paint)
            paint.color = Color.BLACK
            val width = paint.measureText(player.uid)
            c.drawText(player.uid, player.x - width / 2, player.y - radius - 10f, paint)
        }
    }

    fun move(accX: Float, accY: Float, callback: (Float, Float) -> Unit) {
        cX -= accX
        cY += accY

        if (cX <= radius) {
            cX = radius
        }
        if (cX >= boundary.right - radius) {
            cX = boundary.right - radius
        }

        if (cY <= radius) {
            cY = radius
        }
        if (cY >= boundary.bottom - radius) {
            cY = boundary.bottom - radius
        }


        callback.invoke(cX, cY)
    }

    fun update(player: Player) {
        players[player.uid] = player
        invalidate()
    }

    companion object {
        const val logTag = "PlayBoard"
    }
}