package com.tr1984.icebreak.page.play

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import com.tr1984.icebreak.databinding.ActivityPlayBinding
import com.tr1984.icebreak.model.Player
import com.tr1984.icebreak.page.BaseActivity
import com.tr1984.icebreak.util.FirebaseRealtimeDatabaseHelper
import com.tr1984.icebreak.util.SensorHelper
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class PlayActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayBinding
    private lateinit var sensorHelper: SensorHelper
    private val fbHelper = FirebaseRealtimeDatabaseHelper {
        binding.playView.update(it)
    }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityPlayBinding.inflate(layoutInflater)
            .also {
                binding = it
            }.run {
                setContentView(root)
            }

        SensorHelper(this) { accX, accY ->
            binding.playView.move(accX, accY) { cx, cy ->
                fbHelper.write(Player("tris", "#0000ff", cx, cy))
            }
        }.also {
            sensorHelper = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorHelper.destroy()
        fbHelper.destroy()
        disposable?.dispose()
    }
}