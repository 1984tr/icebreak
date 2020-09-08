package com.tr1984.icebreak.page.main

import android.content.Intent
import android.os.Bundle
import com.tr1984.icebreak.databinding.ActivityMainBinding
import com.tr1984.icebreak.page.BaseActivity
import com.tr1984.icebreak.page.play.PlayActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).also {
            binding = it
        }.run {
            setContentView(root)
        }

        binding.btnEnter.setOnClickListener {
            startActivity(Intent(this, PlayActivity::class.java))
        }
    }
}