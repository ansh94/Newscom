package com.anshdeep.newsly.di

import android.arch.lifecycle.ViewModel
import com.anshdeep.newsly.ui.main.MainActivity
import com.anshdeep.newsly.ui.main.categories.CategoryNewsActivity
import com.anshdeep.newsly.ui.main.categories.CategoryViewModel
import com.anshdeep.newsly.ui.main.home.HomeFragmentProvider
import com.anshdeep.newsly.utilities.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by ansh on 22/02/18.
 */

@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [HomeFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindCategoryNewsActivity(): CategoryNewsActivity

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel
}