package com.example.ansh.modernnewsapp.di

import com.example.ansh.modernnewsapp.ModernNewsApp
import com.example.ansh.modernnewsapp.ui.main.MainActivityModule
import com.example.ansh.modernnewsapp.utilities.di.ViewModelBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by ansh on 13/02/18.
 */

// A Component is an interface where we specify from which modules instances
// should be injected in which classes.

// AndroidSupportInjectionModule is the module that helps us to inject instances
// into Android ecosystem classes such are: Activities, Fragments, Services,
// BroadcastReceivers or ContentProviders.
@Singleton
@Component(
        modules = [AndroidSupportInjectionModule::class,
            AppModule::class,
            ViewModelBuilder::class,
            MainActivityModule::class])
interface AppComponent : AndroidInjector<ModernNewsApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ModernNewsApp>()
}