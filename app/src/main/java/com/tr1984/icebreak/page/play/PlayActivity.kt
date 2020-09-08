package com.tr1984.icebreak.page.play

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tr1984.icebreak.databinding.ActivityPlayBinding
import com.tr1984.icebreak.page.BaseActivity

class PlayActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityPlayBinding.inflate(layoutInflater)
            .also {
                binding = it
            }.run {
                setContentView(root)
            }
    }
}