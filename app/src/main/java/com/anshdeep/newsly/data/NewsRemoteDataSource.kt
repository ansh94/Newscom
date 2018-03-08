package com.anshdeep.newsly.data

import com.anshdeep.newsly.api.NewsService
import com.anshdeep.newsly.model.NewsResult
import io.reactivex.Observable

/**
 * Created by ansh on 13/02/18.
 */
class NewsRemoteDataSource {

    private val newsService: NewsService = NewsService.getNewsService()

    fun getRepositories(): Observable<NewsResult> {


        return newsService.getTopHeadlines()

//        var arrayList = ArrayList<News>()
//        arrayList.add(News("BMW India Rolls Out The 6 Series GT From Its Chennai Plant", "Author 1", "test", null, "2018-02-26T04:32:59Z"))
//        arrayList.add(News("Sample News Remote 2", "Author 2", "test", null, "2018-02-26T06:04:49Z"))
//        arrayList.add(News("Sample News Remote 3", "Author 3", "test", null, "2018-02-26T06:04:49Z"))
//
//
//        // Observable.just() takes an item and creates an observable that emits that item.
//        // .delay(2,TimeUnit.SECONDS) in code makes our Observables to wait 2 seconds before
//        // starting to emit the data.
//        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }
}