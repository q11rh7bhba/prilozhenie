package com.gaz.activ.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkerUtils {

    fun createWork(context: Context) {
        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(
                3, TimeUnit.HOURS,
                1, TimeUnit.MINUTES
            )
                .build()

        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                "showNotification",
                ExistingPeriodicWorkPolicy.KEEP,
                notificationWorkRequest
            )
    }
}