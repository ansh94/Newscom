package com.anshdeep.newsly.model

import com.google.gson.annotations.SerializedName

data class NewsResult(
        @SerializedName("status") val status: String,
        @SerializedName("totalResults") val totalResults: Int,
        @SerializedName("articles") val articles: List<Articles>
)

data class Articles(
        @SerializedName("source") val source: Source,
        @SerializedName("author") val author: String?,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String?,
        @SerializedName("url") val url: String,
        @SerializedName("urlToImage") val urlToImage: String?,
        @SerializedName("publishedAt") val publishedAt: String
)

data class Source(
        @SerializedName("id") val id: String?,
        @SerializedName("name") val name: String
)