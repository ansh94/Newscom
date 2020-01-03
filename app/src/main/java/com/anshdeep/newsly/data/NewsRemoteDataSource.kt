package com.anshdeep.newsly.data

import android.util.Log
import com.anshdeep.newsly.api.NewsService
import com.anshdeep.newsly.model.NewsResult

/**
 * Created by ansh on 13/02/18.
 */
class NewsRemoteDataSource(var newsService: NewsService) {

    suspend fun getTopHeadlines(): NewsResult {
        Log.d("NewsRemoteSource", "in get top headlines remote")
        return newsService.getTopHeadlines()
    }

    suspend fun getHeadlinesByCategory(category: String): NewsResult {
        return newsService.getHeadlinesByCategory(category)
    }

    suspend fun getHeadlinesByKeyword(keyword: String): NewsResult {
        return newsService.getHeadlinesByKeyword(keyword)
    }
}