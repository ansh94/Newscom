package com.example.ansh.modernnewsapp.ui.main

import android.arch.lifecycle.ViewModel
import com.example.ansh.modernnewsapp.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by ansh on 13/02/18.
 */


@Module
internal abstract class MainActivityModule {

    // @ContributesAndroidInjector annotation helps Dagger to wire up what is needed
    // so we can inject instances in the specified activity.
    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}