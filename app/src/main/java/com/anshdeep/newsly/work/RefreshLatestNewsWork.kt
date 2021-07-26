package com.anshdeep.newsly.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.anshdeep.newsly.data.NewsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Created by ansh on 2019-07-10.
 */
@HiltWorker
class RefreshLatestNewsWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val newsRepository: NewsRepository
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshLatestNewsWork"
    }


    /**
     * A coroutine-friendly method to do our work.
     */
    override suspend fun doWork(): Result {
        return try {
            // deleting stale data in offline cache (database)
            newsRepository.deleteOldHeadlinesData()

            // updating fresh data in cache
            newsRepository.getTopHeadlines()
            Log.d(WORK_NAME, "Work success")
            Result.success()
        } catch (e: Exception) {
            Log.d(WORK_NAME, "Work Exception: " + e.message)
            Result.failure()
        }
    }
}