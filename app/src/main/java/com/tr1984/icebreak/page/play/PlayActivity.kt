package com.tr1984.icebreak.page.play

import android.os.Bundle
import com.tr1984.icebreak.databinding.ActivityPlayBinding
import com.tr1984.icebreak.page.BaseActivity
import com.tr1984.icebreak.util.SensorHelper

class PlayActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayBinding
    private lateinit var sensorHelper: SensorHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityPlayBinding.inflate(layoutInflater)
            .also {
                binding = it
            }.run {
                setContentView(root)
            }

        SensorHelper(this) { accX, accY ->
            binding.playView.update(accX, accY)
            binding.txtInfo.text = "accX: ${String.format("%.1f", accX)}\naccY: ${String.format("%.1f", accY)}"
        }.also {
            sensorHelper = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorHelper.destroy()
    }
}