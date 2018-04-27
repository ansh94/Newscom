package com.anshdeep.newsly.ui.main.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.model.Articles
import com.anshdeep.newsly.model.NewsResult
import com.anshdeep.newsly.ui.SingleLiveEvent
import com.anshdeep.newsly.utilities.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ansh on 16/03/18.
 */
class SearchViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {

    // ObservableField is a class from Data Binding library that we can use instead of
    // creating an Observable object. It wraps the object that we would like to be observed.
    val isLoading = ObservableField(false)

    var key: String = "empty"

    var news = MutableLiveData<List<Articles>>()

    // CompositeDisposable, a disposable container that can hold onto multiple other disposables
    private val compositeDisposable = CompositeDisposable()

    private val status = SingleLiveEvent<Status>()


    fun getStatus(): LiveData<Status> {
        return status
    }


    fun loadNewsByKeyword(keyword: String) {
        isLoading.set(true)

        compositeDisposable += newsRepository
                .getHeadlinesByKeyword(keyword)
                .subscribeOn(Schedulers.io())   // Background thread
                .observeOn(AndroidSchedulers.mainThread()) // Android work on ui thread
                .subscribeWith(object : DisposableObserver<NewsResult>() {

                    override fun onError(e: Throwable) {
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                        Log.d("SearchViewModel", "Erorr: " + e.message)
                        isLoading.set(false)

                        news.value = arrayListOf()


                        if (e.message!!.contains("Unable to resolve host")) {
                            status.value = Status.NO_NETWORK
                        } else {
                            status.value = Status.ERROR
                        }

                    }

                    // called every time observable emits the data
                    override fun onNext(data: NewsResult) {
                        Log.d("SearchViewModel", "in on next()")
                        Log.d("SearchViewModel", "size: " + data.totalResults)

                        if (data.totalResults == 0) {
                            status.value = Status.NO_RESULTS
                        } else {
                            status.value = Status.SUCCESS
                        }

                        news.value = data.articles

                    }

                    // called when observable finishes emitting all the data
                    override fun onComplete() {
                        isLoading.set(false)
                    }
                })
    }


    // In ViewModel's onCleared() method we should dispose all our disposables in CompositeDisposable
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}