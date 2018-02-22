package com.example.ansh.modernnewsapp.di

import com.example.ansh.modernnewsapp.ui.main.HomeFragmentProvider
import com.example.ansh.modernnewsapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ansh on 22/02/18.
 */

@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [HomeFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity
}