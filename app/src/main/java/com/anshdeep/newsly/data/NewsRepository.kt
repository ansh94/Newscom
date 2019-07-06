package com.anshdeep.newsly.data

import com.anshdeep.newsly.androidmanagers.NetManager
import com.anshdeep.newsly.model.NewsResult
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by ansh on 13/02/18.
 */

/*
The only thing that repository needs to know is that the data is coming from remote or local.
There is no need to know how we are getting those remote or local data.
 */
class NewsRepository @Inject constructor(var netManager: NetManager, var remoteDataSource: NewsRemoteDataSource) {

    fun getTopHeadlines(): Single<NewsResult> {
        return remoteDataSource.getTopHeadlines()
    }

    fun getHeadlinesByCategory(category: String): Single<NewsResult> {
        return remoteDataSource.getHeadlinesByCategory(category)
    }

    fun getHeadlinesByKeyword(keyword: String): Single<NewsResult> {
        return remoteDataSource.getHeadlinesByKeyword(keyword)
    }
}