package com.anshdeep.newsly.data

import com.anshdeep.newsly.androidmanagers.NetManager
import com.anshdeep.newsly.model.NewsResult
import javax.inject.Inject

/**
 * Created by ansh on 13/02/18.
 */

/*
The only thing that repository needs to know is that the data is coming from remote or local.
There is no need to know how we are getting those remote or local data.
 */
class NewsRepository @Inject constructor(var netManager: NetManager, var remoteDataSource: NewsRemoteDataSource) {

    suspend fun getTopHeadlines(): NewsResult {
        return remoteDataSource.getTopHeadlines()
    }

    suspend fun getHeadlinesByCategory(category: String): NewsResult {
        return remoteDataSource.getHeadlinesByCategory(category)
    }

    suspend fun getHeadlinesByKeyword(keyword: String): NewsResult {
        return remoteDataSource.getHeadlinesByKeyword(keyword)
    }
}