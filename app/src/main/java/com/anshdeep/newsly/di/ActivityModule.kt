package com.anshdeep.newsly.di

import com.anshdeep.newsly.ui.main.MainActivity
import com.anshdeep.newsly.ui.main.home.HomeFragmentProvider
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