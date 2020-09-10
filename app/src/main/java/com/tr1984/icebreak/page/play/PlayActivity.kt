package com.tr1984.icebreak.page.play

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.tr1984.icebreak.databinding.ActivityPlayBinding
import com.tr1984.icebreak.model.Player
import com.tr1984.icebreak.page.BaseActivity
import com.tr1984.icebreak.util.FirebaseRealtimeDatabaseHelper
import com.tr1984.icebreak.util.SensorHelper

class PlayActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayBinding
    private lateinit var sensorHelper: SensorHelper
    private val fbHelper = FirebaseRealtimeDatabaseHelper {
        binding.playView.update(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityPlayBinding.inflate(layoutInflater)
            .also {
                binding = it
            }.run {
                setContentView(root)
            }

        SensorHelper(this) { accX, accY ->
            fbHelper.write(Player("a", Color.RED, accX, accY))
        }.also {
            sensorHelper = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorHelper.destroy()
        fbHelper.destroy()
    }
}