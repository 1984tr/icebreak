package com.tr1984.icebreak.page.play

import android.os.Bundle
import com.tr1984.icebreak.databinding.ActivityPlayBinding
import com.tr1984.icebreak.model.Player
import com.tr1984.icebreak.page.BaseActivity
import com.tr1984.icebreak.util.FirebaseRealtimeDatabaseHelper

class PlayActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayBinding
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

                joystick.positionCallback = { dx, dy ->
                    binding.playView.move(dx, dy) { cx, cy ->
                        if (IS_SIMULATE) {
                            binding.playView.update(Player("tris", "#0000ff", cx, cy))
                        } else {
                            fbHelper.write(Player("tris", "#0000ff", cx, cy))
                        }
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        fbHelper.destroy()
    }

    companion object {
        const val IS_SIMULATE = true
    }
}