package com.gaz.activ.utils

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.gaz.activ.di.NotificationComponent
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class NotificationWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : RxWorker(appContext, workerParams) {

    private val notificationComponent by lazy { NotificationComponent() }

    override fun createWork() : Single<Result> =
        notificationComponent.settingRepository
            .getRegistrationMarker()
            .flatMap {
                if (it) Single.fromCallable { Result.success() } else showNotification()
            }

    private fun showNotification() =
        Completable.fromCallable {
            val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            if (time in 9..18) NotificationUtils.showNotification(appContext)
        }.andThen(Single.fromCallable { Result.success() })
}