package com.anshdeep.newsly.api

import com.anshdeep.newsly.BuildConfig
import com.anshdeep.newsly.model.NewsResult
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ansh on 08/03/18.
 */
interface NewsService {

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getTopHeadlines(): Observable<NewsResult>

    @GET("top-headlines?country=in&apiKey=" + BuildConfig.NEWS_API_KEY)
    fun getHeadlinesByCategory(@Query("category") category: String): Observable<NewsResult>


    /**
     * Kinda like a static block in Java
     */
    companion object Factory {
        private const val BASE_URL = "https://newsapi.org/v2/"

        // TODO: Move to DI.
        fun getNewsService(): NewsService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(NewsService::class.java)
        }
    }
}