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