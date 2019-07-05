package com.anshdeep.newsly.ui.main.search

import androidx.lifecycle.ViewModel
import com.anshdeep.newsly.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by ansh on 16/03/18.
 */

@Module
internal abstract class SearchFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideSearchFragment(): SearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}