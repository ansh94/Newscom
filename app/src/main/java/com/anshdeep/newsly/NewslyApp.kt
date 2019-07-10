package com.anshdeep.newsly

import android.os.Build
import androidx.work.*
import com.anshdeep.newsly.di.DaggerAppComponent
import com.anshdeep.newsly.utilities.work.MyWorkerFactory
import com.anshdeep.newsly.work.RefreshLatestNewsWork
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ansh on 13/02/18.
 */
class NewslyApp : DaggerApplication() {

    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()

        /*For one-time work request
        val oneTimeRequest = OneTimeWorkRequestBuilder<RefreshLatestNewsWork>()
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueue(oneTimeRequest)
        */

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshLatestNewsWork>(3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshLatestNewsWork.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()

        // setup custom work manager configuration
        WorkManager.initialize(this, Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .setWorkerFactory(myWorkerFactory).build())
        delayedInit()
    }
}