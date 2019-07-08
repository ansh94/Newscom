package com.anshdeep.newsly.ui.main.search

import android.util.Log
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
 * Created by ansh on 16/03/18.
 */
class SearchViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    var news = MutableLiveData<List<Articles>>()

    private val status = SingleLiveEvent<Status>()


    fun getStatus(): LiveData<Status> {
        return status
    }

    fun loadNewsByKeyword(keyword: String) {
        viewModelScope.launch {
            try {
                isLoading.set(true)

                val newsResult = newsRepository.getHeadlinesByKeyword(keyword)
                Log.d("SearchViewModel", "Success()")
                Log.d("SearchViewModel", "size: " + newsResult.totalResults)
                if (newsResult.totalResults == 0) {
                    status.value = Status.NO_RESULTS
                } else {
                    status.value = Status.SUCCESS
                }
                news.value = newsResult.articles
                isLoading.set(false)

            } catch (e: Exception) {
                // if some error happens in our data layer our app will not crash, we will
                // get error here
                Log.d("SearchViewModel", "Exception: " + e.message)
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

}