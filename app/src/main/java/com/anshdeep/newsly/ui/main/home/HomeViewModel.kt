package com.anshdeep.newsly.ui.main.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.ui.SingleLiveEvent
import kotlinx.coroutines.launch
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

    var news = newsRepository.news

    private val status = SingleLiveEvent<Status>()


    fun getStatus(): LiveData<Status> {
        return status
    }

    init {
        loadTopHeadlines()
    }

    private fun loadTopHeadlines() {
        viewModelScope.launch {
            try {
                // to load news articles first time if db is empty else
                // we show from db to prevent network calls everytime
                // Database is the single source of truth
                if (newsRepository.getLatestNewsSize() == 0) {
                    isLoading.set(true)
                    newsRepository.getTopHeadlines()
                    status.value = Status.SUCCESS
                    isLoading.set(false)
                }

            } catch (e: Exception) {
                isLoading.set(false)
                status.value = Status.ERROR
            }
        }
    }

    // for swipe to refresh
    fun onRefresh() {
        viewModelScope.launch {
            try {
                isRefreshing.set(true)

                newsRepository.getTopHeadlines()
                if (newsRepository.news.value!!.isEmpty()) {
                    status.value = Status.ERROR
                } else {
                    status.value = Status.SUCCESS
                }
                isRefreshing.set(false)

            } catch (e: Exception) {
                isRefreshing.set(false)
                status.value = Status.ERROR
            }
        }
    }

}