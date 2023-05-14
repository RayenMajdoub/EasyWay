package com.example.easyway.Utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class NotificationPermissionHelper (){
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkNotificationPermission(context: Activity) {
        if ( ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED)
        {

        }
        else{
            ActivityCompat.requestPermissions(context, arrayOf(  Manifest.permission.POST_NOTIFICATIONS),5)
        }
    }
    companion object {
        private const val POST_NOTIFICATIONS_REQUEST_CODE = 10


    }
}