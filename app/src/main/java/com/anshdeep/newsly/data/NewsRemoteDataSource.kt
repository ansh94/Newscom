package com.anshdeep.newsly.data

import com.anshdeep.newsly.ui.uimodels.News
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by ansh on 13/02/18.
 */
class NewsRemoteDataSource {

    fun getRepositories(): Observable<ArrayList<News>> {
        var arrayList = ArrayList<News>()
        arrayList.add(News("Sample News Remote 1", "Author 1", "test", null, "2 hours ago"))
        arrayList.add(News("Sample News Remote 2", "Author 2", "test", null, "2 hours ago"))
        arrayList.add(News("Sample News Remote 3", "Author 3", "test", null, "2 hours ago"))


        // Observable.just() takes an item and creates an observable that emits that item.
        // .delay(2,TimeUnit.SECONDS) in code makes our Observables to wait 2 seconds before
        // starting to emit the data.
        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }
}