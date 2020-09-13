package com.tr1984.icebreak

import android.graphics.PointF
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Math.PI
import kotlin.math.atan2

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // x = cos(angle) * radius
    // y = sin(angle) * radius
    @Test
    fun sinTest() {
        val center = PointF(0f, 0f)
        val radius = 2f
        System.out.println("top: ${x(90, radius)}, ${y(90, radius)}")
        System.out.println("right: ${x(0, radius)}, ${y(0, radius)}")
        System.out.println("bottom: ${x(270, radius)}, ${y(270, radius)}")
        System.out.println("left: ${x(180, radius)}, ${y(180, radius)}")
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
        return if (angle < 0) angle + 360 else angle // make it as a regular counter-clock protractor
    }
}