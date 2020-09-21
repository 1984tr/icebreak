package com.tr1984.icebreak.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.atan2

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

    private var lastDx = 0f
    private var lastDy = 0f

    private var validDx = 0f
    private var validDy = 0f

    var positionCallback: ((Float, Float) -> Unit)? = null
    var timerDisposable: Disposable? = null

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
            pointerRadius = radius / 4
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val e = event ?: return true
        var x = e.x
        var y = e.y
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                oldX = event.x
                oldY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                pointer.x += (x - oldX)
                pointer.y += (y - oldY)

                // 1. 중심점과 현재 좌표의 각도 구하기
                val pCenter = PointF(0f, 0f)
                val pPoint = PointF(pointer.x - center.x, -(pointer.y - center.y))
                val angle = getAngle(pCenter, pPoint)
                val maxX = x(angle, radius - pointerRadius)
                val maxY = y(angle, radius - pointerRadius)
                if (pPoint.x >= 0) {
                    if (pPoint.x > maxX) {
                        pointer.x = maxX + center.x
                        //x = pointer.x
                    }
                } else {
                    if (pPoint.x < maxX) {
                        pointer.x = maxX + center.x
                        //x = pointer.x
                    }
                }
                if (pPoint.y >= 0) {
                    if (pPoint.y > maxY) {
                        pointer.y = -maxY + center.y
                        // y = pointer.y
                    }
                } else {
                    if (pPoint.y < maxY) {
                        pointer.y = -maxY + center.y
                        //y = pointer.y
                    }
                }

                lastDx = -(x - oldX)
                lastDy = y - oldY

                val absX = abs(lastDx)
                val absY = abs(lastDy)
                if (abs(absX - absY) >= 3) {
                    validDx = lastDx
                    validDy = lastDy
                } else {
                    if (abs(lastDx) > 2) {
                        validDx = lastDx
                    }
                    if (abs(lastDy) > 2) {
                        validDy = lastDy
                    }
                }

                positionCallback?.invoke(lastDx, lastDy)
                Log.d("test", "lastDx: $lastDx, lastDy: $lastDy")
                timerDisposable?.dispose()
                timerDisposable = Observable.interval(10L, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        positionCallback?.invoke(validDx, validDy)
                    }, { it.printStackTrace() })

                oldX = x
                oldY = y
            }
            MotionEvent.ACTION_UP -> {
                timerDisposable?.dispose()
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
        c.drawCircle(center.x, center.y, radius - pointerRadius, paint)

        with(paint) {
            color = Color.LTGRAY
            style = Paint.Style.FILL_AND_STROKE
        }
        c.drawCircle(pointer.x, pointer.y, pointerRadius, paint)
    }

    private fun x(angle: Int, radius: Float): Int {
        return (kotlin.math.cos(toRadians(angle)) * radius).toInt()
    }

    private fun y(angle: Int, radius: Float): Int {
        return (kotlin.math.sin(toRadians(angle)) * radius).toInt()
    }

    private fun toRadians(deg: Int): Double {
        return deg.toFloat() / 180.0 * Math.PI
    }

    private fun getAngle(center: PointF, point: PointF): Int {
        val angle =
            Math.toDegrees(atan2((point.y - center.y).toDouble(), (point.x - center.x).toDouble()))
                .toInt()
        return if (angle < 0) angle + 360 else angle
    }
}