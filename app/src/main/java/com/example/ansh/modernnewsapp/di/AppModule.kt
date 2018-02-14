package com.example.ansh.modernnewsapp.di

import android.content.Context
import com.example.ansh.modernnewsapp.ModernNewsApp
import dagger.Module
import dagger.Provides

/**
 * Created by ansh on 13/02/18.
 */

@Module
class AppModule {

    @Provides
    fun providesContext(application: ModernNewsApp): Context {
        return application.applicationContext
    }
}