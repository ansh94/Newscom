package com.anshdeep.newsly.di

import android.content.Context
import com.anshdeep.newsly.data.LatestNewsDao
import com.anshdeep.newsly.data.LatestNewsDatabase
import com.anshdeep.newsly.data.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by ansh on 2021-07-24.
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): LatestNewsDatabase {
        return getDatabase(context)
    }

    @Provides
    fun provideLatestNewsDao(appDatabase: LatestNewsDatabase): LatestNewsDao {
        return appDatabase.latestNewsDao()
    }

}