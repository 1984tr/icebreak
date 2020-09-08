package com.tr1984.icebreak.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.tr1984.icebreak.util.Logger

class PlayBoard : View {

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        Logger.d(logTag, "PlayBoard initialized")
    }

    companion object {
        const val logTag = "PlayBoard"
    }
}