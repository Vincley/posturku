package com.capstone.posturku.model.news

data class NewsDataFromJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)