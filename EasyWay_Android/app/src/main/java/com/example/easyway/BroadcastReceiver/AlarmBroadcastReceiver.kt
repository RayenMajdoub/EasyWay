package com.example.easyway.BroadcastReceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.easyway.R
import com.example.easyway.View.AlarmActivity


class AlarmBroadcastReceiver : BroadcastReceiver() {
    var title: String? = null
    var desc: String? = null
    var date: String? = null
    var time: String? = null
    private  val NOTIFICATION_ID = "easy_way"
    @SuppressLint("SuspiciousIndentation", "MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        title = intent.getStringExtra("TITLE")
        desc = intent.getStringExtra("DESC")
        date = intent.getStringExtra("DATE")
        time = intent.getStringExtra("TIME")
        if (intent.action.equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            Toast.makeText(context, "Alarm just rang...", Toast.LENGTH_SHORT).show();
        }

     /*val notification:NotificationCompat.Builder  =  NotificationCompat.Builder(context, NOTIFICATION_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Name")
            .setContentText("Name")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(200, notification.build())
        }*/


        Toast.makeText(context, "Broadcast receiver called", Toast.LENGTH_SHORT).show();
        val i = Intent(context, AlarmActivity::class.java)
        i.putExtra("TITLE", title)
        i.putExtra("DESC", desc)
        i.putExtra("DATE", date)
        i.putExtra("TIME", time)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }
}