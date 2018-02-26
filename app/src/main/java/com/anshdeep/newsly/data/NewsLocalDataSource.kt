package com.anshdeep.newsly.data

import com.anshdeep.newsly.ui.uimodels.News
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by ansh on 13/02/18.
 */
class NewsLocalDataSource {

    fun getRepositories(): Observable<ArrayList<News>> {
        var arrayList = ArrayList<News>()
        arrayList.add(News("Sample News Local 1", "Author 1", "test", null, "2018-02-26T06:04:49Z"))
        arrayList.add(News("Sample News Local 2", "Author 2", "test", null,"2018-02-26T06:04:49Z"))
        arrayList.add(News("Sample News Local 3", "Author 3", "test", null,"2018-02-26T06:04:49Z"))

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }

    fun saveRepositories(arrayList: ArrayList<News>): Completable {

        // Single.just(1) will create Single that emits just number 1.
        // Cause we are using delay(1,TimeUnit.SECONDS) emitting will be delayed by 1 second.
        // toCompletable() Returns a Completable that discards result of the Single and calls
        // onComplete when this source Single calls onSuccess
        return Single.just(1).delay(1, TimeUnit.SECONDS).toCompletable()
    }
}