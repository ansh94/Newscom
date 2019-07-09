package com.anshdeep.newsly.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anshdeep.newsly.model.Articles

/**
 * Created by ansh on 2019-07-09.
 */

@Entity
data class DatabaseLatestNews constructor(
        @PrimaryKey
        val url: String,
        val title: String,
        val urlToImage: String?,
        val publishedAt: String)

fun List<DatabaseLatestNews>.asDomainModel(): List<Articles> {
    return map {
        Articles(
                url = it.url,
                title = it.title,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt)
    }
}

