package com.anshdeep.newsly.ui.main.home

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.model.Articles
import com.anshdeep.newsly.model.NewsResult
import com.anshdeep.newsly.ui.SingleLiveEvent
import com.anshdeep.newsly.utilities.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ansh on 13/02/18.
 */

// All of our view models should extend the ViewModel() class
class HomeViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {

    // ObservableField is a class from Data Binding library that we can use instead of
    // creating an Observable object. It wraps the object that we would like to be observed.
    val isLoading = ObservableField(false)

    val isRefreshing = ObservableBoolean(false)


    var news = MutableLiveData<List<Articles>>()

    // CompositeDisposable, a disposable container that can hold onto multiple other disposables
    private val compositeDisposable = CompositeDisposable()


    private val status = SingleLiveEvent<Status>()


    fun getStatus(): LiveData<Status> {
        return status
    }

    fun getNewsItemCount(): Int? {
        return news.value?.size
    }


    init {
        // to load news articles first time
        loadTopHeadlines()
    }

    private fun loadTopHeadlines() {
        isLoading.set(true)

        // we can choose which thread will observable operate on using subscribeOn() method and
        // which thread observer will operate on using observeOn() method. Usually, all code
        // from data layer should be operated on background thread.
        compositeDisposable += newsRepository
                .getTopHeadlines()
                .subscribeOn(Schedulers.io())   // Background thread
                .observeOn(AndroidSchedulers.mainThread()) // Android work on ui thread
                .subscribeWith(object : DisposableSingleObserver<NewsResult>() {
                    override fun onSuccess(data: NewsResult) {
                        Log.d("HomeViewModel", "in onSuccess()")
                        status.value = Status.SUCCESS
                        news.value = data.articles
                        isLoading.set(false)
                    }

                    override fun onError(e: Throwable) {
                        // if some error happens in our data layer our app will not crash, we will
                        // get error here
                        Log.d("HomeVieWModel", "onError: " + e.message)
                        isLoading.set(false)

                        news.value = arrayListOf()

                        if (e.message!!.contains("Unable to resolve host")) {
                            status.value = Status.NO_NETWORK
                        } else {
                            status.value = Status.ERROR
                        }
                    }

                })
    }

    // for swipe to refresh
    fun onRefresh() {
        isRefreshing.set(true)

        // we can choose which thread will observable operate on using subscribeOn() method and
        // which thread observer will operate on using observeOn() method. Usually, all code
        // from data layer should be operated on background thread.
        compositeDisposable += newsRepository
                .getTopHeadlines()
                .subscribeOn(Schedulers.io())   // Background thread
                .observeOn(AndroidSchedulers.mainThread()) // Android work on ui thread
                .subscribeWith(object : DisposableSingleObserver<NewsResult>() {
                    override fun onSuccess(data: NewsResult) {
                        Log.d("HomeViewModel", "in onSuccess()")
                        status.value = Status.SUCCESS
                        news.value = data.articles
                        isRefreshing.set(false)
                    }

                    override fun onError(e: Throwable) {
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                        isRefreshing.set(false)
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