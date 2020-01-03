package com.anshdeep.newsly.utilities.work

import com.anshdeep.newsly.work.RefreshLatestNewsWork
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by ansh on 2019-07-10.
 */

@Module
abstract class WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshLatestNewsWork::class)
    internal abstract fun bindMyWorkerFactory(factory: RefreshLatestNewsWork.Factory): ChildWorkerFactory
}