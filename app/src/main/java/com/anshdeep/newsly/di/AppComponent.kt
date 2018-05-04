package com.anshdeep.newsly.di

import com.anshdeep.newsly.NewslyApp
import com.anshdeep.newsly.utilities.di.ViewModelBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by ansh on 13/02/18.
 */

// A Component is an interface where we specify from which modules instances
// should be injected.

// AndroidSupportInjectionModule is the module that helps us to inject instances
// into Android ecosystem classes such are: Activities, Fragments, Services,
// BroadcastReceivers or ContentProviders.
@Singleton
@Component(
        modules = [AndroidSupportInjectionModule::class,
            AppModule::class,
            ViewModelBuilder::class,
            ActivityModule::class,
            NetworkModule::class])
interface AppComponent : AndroidInjector<NewslyApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<NewslyApp>()
}