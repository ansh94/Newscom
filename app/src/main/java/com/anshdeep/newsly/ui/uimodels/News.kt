package com.anshdeep.newsly.ui.uimodels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.anshdeep.newsly.BR

/**
 * Created by ansh on 13/02/18.
 */

// Data Binding library provide us with BaseObservable class which
// was created to implement the listener registration mechanism
class News(newsTitle: String, var newsAuthor: String?, var newsUrl: String,
           var newsImageUrl: String?, var newsPublishTime: String) : BaseObservable() {

    // The Bindable annotation generates an entry in the BR class file during compilation.
    @get:Bindable
    var newsTitle: String = newsTitle
        set(value) {
            field = value
            notifyPropertyChanged(BR.newsTitle)
        }
}