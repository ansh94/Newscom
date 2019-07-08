package com.anshdeep.newsly.api

import com.anshdeep.newsly.BuildConfig
import com.anshdeep.newsly.model.NewsResult
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by ansh on 08/03/18.
 */
interface NewsService {

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    suspend fun getTopHeadlines(): NewsResult

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    suspend fun getHeadlinesByCategory(@Query("category") category: String): NewsResult

    @GET("top-headlines?language=en&apiKey=" + BuildConfig.NEWS_API_KEY)
    suspend fun getHeadlinesByKeyword(@Query("q") keyword: String): NewsResult

}