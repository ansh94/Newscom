package com.anshdeep.newsly.api

import com.anshdeep.newsly.BuildConfig
import com.anshdeep.newsly.model.NewsResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by ansh on 08/03/18.
 */
interface NewsService {

    /*
    Single is an Observable that always emit only one value or throws an error.
    A typical use case of Single observable would be when we make a network call in Android
    and receive a response.
     */

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getTopHeadlines(): Single<NewsResult>

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getHeadlinesByCategory(@Query("category") category: String): Single<NewsResult>

    @GET("top-headlines?language=en&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getHeadlinesByKeyword(@Query("q") keyword: String): Single<NewsResult>

}