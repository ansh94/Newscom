package com.anshdeep.newsly.ui.main.categories

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshdeep.newsly.data.NewsRepository
import com.anshdeep.newsly.model.Articles
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ansh on 09/03/18.
 */
class CategoryViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    var category: String? = null

    var news = MutableLiveData<List<Articles>>()

    fun setNewsCategory(category: String) {
        this.category = category
        loadCategoryNews(category)
    }

    fun getNewsCategory(): String? {
        return this.category
    }

    private fun loadCategoryNews(category: String) {
        viewModelScope.launch {
            try {
                isLoading.set(true)

                val newsResult = newsRepository.getHeadlinesByCategory(category)
                news.value = newsResult.articles
                isLoading.set(false)

            } catch (e: Exception) {
                isLoading.set(false)

                // if some error happens in our data layer our app will not crash, we will
                // get error here
                Log.d("CategoryViewModel", "Exception: " + e.message)
            }

        }
    }
}