package com.example.ansh.modernnewsapp.ui.main

import android.arch.lifecycle.ViewModel
import com.example.ansh.modernnewsapp.utilities.di.ViewModelKey
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
    internal abstract fun provideHomeFragment(): FragmentHome

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}