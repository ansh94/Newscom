package com.anshdeep.newsly.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.utilities.work.ChildWorkerFactory
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by ansh on 2019-07-10.
 */
class RefreshLatestNewsWork(
        appContext: Context,
        params: WorkerParameters,
        private val newsRepository: NewsRepository) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshLatestNewsWork"
    }


    /**
     * A coroutine-friendly method to do our work.
     */
    override suspend fun doWork(): Result {
        return try {
            newsRepository.getTopHeadlines()
            Log.d(WORK_NAME, "Work success")
            Result.success()
        } catch (e: Exception) {
            Log.d(WORK_NAME, "Exception: " + e.message)
            Result.failure()
        }
    }

    // for injecting repository using Dagger2
    // custom worker factory
    class Factory @Inject constructor(
            private val newsRepository: Provider<NewsRepository>
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return RefreshLatestNewsWork(appContext, params, newsRepository.get())
        }
    }
}