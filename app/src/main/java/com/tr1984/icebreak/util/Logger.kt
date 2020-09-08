package com.tr1984.icebreak.util

import android.util.Log
import com.tr1984.icebreak.BuildConfig

object Logger {

    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }
}