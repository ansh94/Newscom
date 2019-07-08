package com.anshdeep.newsly.ui.main.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.model.Articles
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


    var news = MutableLiveData<List<Articles>>()

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
        viewModelScope.launch {
            try {
                isLoading.set(true)

                val newsResult = newsRepository.getTopHeadlines()
                status.value = Status.SUCCESS
                news.value = newsResult.articles
                isLoading.set(false)

            } catch (e: Exception) {
                isLoading.set(false)

                news.value = arrayListOf()

                if (e.message!!.contains("Unable to resolve host")) {
                    status.value = Status.NO_NETWORK
                } else {
                    status.value = Status.ERROR
                }
            }
        }
    }

    // for swipe to refresh
    fun onRefresh() {
        viewModelScope.launch {
            try {
                isRefreshing.set(true)

                val newsResult = newsRepository.getTopHeadlines()
                status.value = Status.SUCCESS
                news.value = newsResult.articles
                isRefreshing.set(false)

            } catch (e: Exception) {
                isRefreshing.set(false)
            }
        }
    }

}