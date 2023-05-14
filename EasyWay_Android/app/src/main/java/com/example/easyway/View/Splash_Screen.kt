package com.example.easyway.View

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import com.example.easyway.BroadcastReceiver.AlarmBroadcastReceiver
import com.example.easyway.R
import com.example.easyway.Repository.SessionManager

class Splash_Screen : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 4000 //3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val receiver = ComponentName(this, AlarmBroadcastReceiver::class.java)
        val pm = packageManager
        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Home::class.java))
                finish()
            }, SPLASH_TIME_OUT)
        }
        else {

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Login::class.java))
                finish()
            }, SPLASH_TIME_OUT)
        }

    }


}