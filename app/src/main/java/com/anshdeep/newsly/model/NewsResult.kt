package com.anshdeep.newsly.model

import com.anshdeep.newsly.data.DatabaseLatestNews

data class NewsResult(
        val status: String,
        val totalResults: Int,
        val articles: List<Articles>
)

data class Articles(
        val title: String,
        val url: String,
        val urlToImage: String?,
        val publishedAt: String
)

fun NewsResult.asDatabaseModel(): Array<DatabaseLatestNews> {
    return articles.map {
        DatabaseLatestNews(
                url = it.url,
                title = it.title,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt)
    }.toTypedArray()
}