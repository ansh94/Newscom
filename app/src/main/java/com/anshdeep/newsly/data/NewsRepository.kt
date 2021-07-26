package com.anshdeep.newsly.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.anshdeep.newsly.model.Articles
import com.anshdeep.newsly.model.NewsResult
import com.anshdeep.newsly.model.asDatabaseModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ansh on 13/02/18.
 */

/*
The only thing that repository needs to know is that the data is coming from remote or local.
There is no need to know how we are getting those remote or local data.
 */
@Singleton
class NewsRepository @Inject constructor(var remoteDataSource: NewsRemoteDataSource,
                                         var latestNewsDao: LatestNewsDao) {

    val news: LiveData<List<Articles>> =
            Transformations.map(latestNewsDao.getLatestNews()) {
                it.asDomainModel()
            }

    suspend fun getTopHeadlines() {
        // get latest news from server
        val latestNews = remoteDataSource.getTopHeadlines()

        // insert latest news in database
       latestNewsDao.insertAllLatestNews(*latestNews.asDatabaseModel())

    }

    suspend fun deleteOldHeadlinesData() {
        latestNewsDao.clear()
    }

    suspend fun getLatestNewsSize(): Int {
        return latestNewsDao.getLatestNewsSize()
    }

    suspend fun getHeadlinesByCategory(category: String): NewsResult {
        return remoteDataSource.getHeadlinesByCategory(category)
    }

    suspend fun getHeadlinesByKeyword(keyword: String): NewsResult {
        return remoteDataSource.getHeadlinesByKeyword(keyword)
    }
}