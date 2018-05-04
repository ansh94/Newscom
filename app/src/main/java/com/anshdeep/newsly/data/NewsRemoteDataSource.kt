package com.anshdeep.newsly.data

import com.anshdeep.newsly.api.NewsService
import com.anshdeep.newsly.model.NewsResult
import io.reactivex.Observable

/**
 * Created by ansh on 13/02/18.
 */
class NewsRemoteDataSource(var newsService: NewsService) {

    fun getRepositories(): Observable<NewsResult> {
        return newsService.getTopHeadlines()
    }

    fun getHeadlinesByCategory(category: String): Observable<NewsResult> {
        return newsService.getHeadlinesByCategory(category)
    }

    fun getHeadlinesByKeyword(keyword: String): Observable<NewsResult> {
        return newsService.getHeadlinesByKeyword(keyword)
    }
}