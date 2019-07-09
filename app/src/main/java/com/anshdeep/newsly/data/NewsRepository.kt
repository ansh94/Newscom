package com.anshdeep.newsly.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.anshdeep.newsly.androidmanagers.NetManager
import com.anshdeep.newsly.model.Articles
import com.anshdeep.newsly.model.NewsResult
import com.anshdeep.newsly.model.asDatabaseModel
import javax.inject.Inject

/**
 * Created by ansh on 13/02/18.
 */

/*
The only thing that repository needs to know is that the data is coming from remote or local.
There is no need to know how we are getting those remote or local data.
 */
class NewsRepository @Inject constructor(var netManager: NetManager,
                                         var remoteDataSource: NewsRemoteDataSource,
                                         var database: LatestNewsDatabase) {

    val news: LiveData<List<Articles>> =
            Transformations.map(database.latestNewsDao.getLatestNews()) {
                it.asDomainModel()
            }

    suspend fun getTopHeadlines() {
        if (netManager.isConnectedToInternet) {
            // get latest news from server
            val latestNews = remoteDataSource.getTopHeadlines()

            // insert latest news in database
            database.latestNewsDao.insertAllLatestNews(*latestNews.asDatabaseModel())
        }
    }

    suspend fun getLatestNewsSize(): Int {
        return database.latestNewsDao.getLatestNewsSize()
    }

    suspend fun getHeadlinesByCategory(category: String): NewsResult {
        return remoteDataSource.getHeadlinesByCategory(category)
    }

    suspend fun getHeadlinesByKeyword(keyword: String): NewsResult {
        return remoteDataSource.getHeadlinesByKeyword(keyword)
    }
}