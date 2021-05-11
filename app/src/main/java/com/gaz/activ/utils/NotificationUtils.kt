package com.gaz.activ.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.gaz.activ.R
import com.gaz.activ.ui.activity.SplashActivity

object NotificationUtils {

    private const val CHANNEL_ID = "NOTIFICATION_EVENT_CHANNEL_ID"

    fun showNotification(context: Context) {
        val notificationManager = context.notificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createNotificationChannel(context))
        }
        notificationManager.notify(0, createNotification(context))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) =
            NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            ).apply { description = context.getString(R.string.notification_channel_description) }

    private fun createNotification(context: Context) =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_message))
                .setStyle(NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_message)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setContentIntent(getPendingIntent(context))
                .setAutoCancel(true)
                .build()

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = SplashActivity.newIntent(context)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}