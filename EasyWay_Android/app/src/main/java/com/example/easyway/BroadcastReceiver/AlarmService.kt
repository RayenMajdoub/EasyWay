package com.example.easyway.BroadcastReceiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.easyway.R
import com.example.easyway.View.Home
import kotlinx.coroutines.GlobalScope


class AlarmService() : Service() {


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onHandleIntent(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("UnspecifiedImmutableFlag", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    protected fun onHandleIntent(intent: Intent?) {
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                NOTIFICATION_ID,
                "Easy Way",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "YOUR_NOTIFICATION_CHANNEL_DESCRIPTION"
            mNotificationManager!!.createNotificationChannel(channel)

        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_ticket) // notification icon
                .setContentTitle("Check the application") // title for notification
                .setContentText("It's almost time for your trip ") // message for notification
                .setAutoCancel(true) // clear notification after click

        with(NotificationManagerCompat.from(applicationContext)) {
            mNotificationManager.notify(200, mBuilder.build())
        }


    }
    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val NOTIFICATION_ID = "easy_way"
    }
}