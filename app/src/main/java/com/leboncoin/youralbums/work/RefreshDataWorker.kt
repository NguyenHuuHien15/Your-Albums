package com.leboncoin.youralbums.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.leboncoin.youralbums.ServiceLocator
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "com.leboncoin.youralbums.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        try {
            ServiceLocator.provideRepository(applicationContext).refreshAlbums()
            Timber.d("WorkManager: Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}