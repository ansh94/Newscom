package com.anshdeep.newsly.di

import android.content.Context
import com.anshdeep.newsly.NewslyApp
import dagger.Module
import dagger.Provides

/**
 * Created by ansh on 13/02/18.
 */

// Modules are classes that have functions with @Provides annotation.
// We say for those methods that they are providers cause they provide instances.
@Module
class AppModule {

    @Provides
    fun providesContext(application: NewslyApp): Context {
        return application.applicationContext
    }
}