package com.example.easyway.View

import com.example.easyway.R
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.easyway.databinding.ActivityAlarmBinding


class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var mediaPlayer: MediaPlayer
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityAlarmBinding.inflate(layoutInflater)

         setContentView(binding.root)

    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.notification)
        mediaPlayer.start()
        if (getIntent().extras != null) {
            binding.title!!.text = getIntent().getStringExtra("TITLE")
            binding.description!!.text = getIntent().getStringExtra("DESC")
            binding.timeAndData!!.text =
                getIntent().getStringExtra("DATE") + ", " + getIntent().getStringExtra("TIME")
        }
         binding.closeButton.setOnClickListener { view -> finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.release()
    }

    companion object {
        private val inst: AlarmActivity? = null
        fun instance(): AlarmActivity? {
            return inst
        }
    }
}