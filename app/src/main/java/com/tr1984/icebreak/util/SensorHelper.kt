package com.tr1984.icebreak.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorHelper(context: Context, val positionCallback: (Float, Float) -> Unit) {

    private val mgr by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val sensor by lazy {
        mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
    private val eventListener by lazy {
        object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent?) {
                //     -9.9
                // 9.9       -9.9
                //      9.9
                val values = event?.values
                values?.let {
                    val accX = it[0]
                    val accY = it[1]
                    positionCallback.invoke(accX, accY)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Logger.d(logTag, "accuracy: $accuracy")
            }
        }
    }

    init {
        mgr.registerListener(eventListener, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun destroy() {
        mgr.unregisterListener(eventListener)
    }

    companion object {
        const val logTag = "SensorHelper"
    }
}