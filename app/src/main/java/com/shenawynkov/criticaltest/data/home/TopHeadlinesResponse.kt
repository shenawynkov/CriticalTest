package com.shenawynkov.criticaltest.data.home

data class TopHeadlinesResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)