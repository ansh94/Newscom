package com.anshdeep.newsly.ui.main.categories

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.model.Articles
import com.anshdeep.newsly.model.NewsResult
import com.anshdeep.newsly.utilities.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ansh on 09/03/18.
 */
class CategoryViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    var category: String? = null

    var news = MutableLiveData<List<Articles>>()

    // CompositeDisposable, a disposable container that can hold onto multiple other disposables
    private val compositeDisposable = CompositeDisposable()


    fun setNewsCategory(category: String) {
        this.category = category
        loadCategoryNews(category)
    }

    fun getNewsCategory(): String? {
        return this.category
    }

    private fun loadCategoryNews(category: String) {
        isLoading.set(true)

        compositeDisposable += newsRepository
                .getHeadlinesByCategory(category)
                .subscribeOn(Schedulers.io())   // Background thread
                .observeOn(AndroidSchedulers.mainThread()) // Android work on ui thread
                .subscribeWith(object : DisposableSingleObserver<NewsResult>() {
                    override fun onSuccess(data: NewsResult) {
                        Log.d("CategoryViewModel", "in onSuccess()")
                        news.value = data.articles
                        isLoading.set(false)
                    }

                    override fun onError(e: Throwable) {
                        // if some error happens in our data layer our app will not crash, we will
                        // get error here
                        Log.d("CategoryViewModel", "onError: " + e.message)
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