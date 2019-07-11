package com.anshdeep.newsly.ui.main.home

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.ui.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

                // not connected to internet
                if (!newsRepository.netManager.isConnectedToInternet) {
                    status.value = Status.NO_NETWORK
                }

                // internet present and loading first time
                else if (newsRepository.netManager.isConnectedToInternet && newsRepository.getLatestNewsSize() == 0) {
                    isLoading.set(true)
                    newsRepository.getTopHeadlines()
                    isLoading.set(false)
                    status.value = Status.SUCCESS
                } else {
                    // do no network calls as data is present in offline cache
                    // Database is the single source of truth
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

                if (!newsRepository.netManager.isConnectedToInternet) {
                    isRefreshing.set(true)
                    status.value = Status.NO_NETWORK
                    isRefreshing.set(false)
                } else {

                    withContext(Dispatchers.IO) {
                        Log.d("HomeViewModel", "Refreshing news")
                        isRefreshing.set(true)
                        newsRepository.getTopHeadlines()
                        isRefreshing.set(false)
                    }

                    status.value = Status.SUCCESS
                }

            } catch (e: Exception) {
                isRefreshing.set(false)
                status.value = Status.ERROR
            }
        }
    }

}