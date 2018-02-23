package com.anshdeep.newsly.ui.main.home

import android.arch.lifecycle.ViewModel
import com.anshdeep.newsly.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by ansh on 22/02/18.
 */

@Module
internal abstract class HomeFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideHomeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindMainViewModel(viewModel: HomeViewModel): ViewModel
}