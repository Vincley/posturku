package com.capstone.posturku.model.news

import android.os.Parcelable
import com.capstone.posturku.model.news.entities.Article
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("totalResults")
    val totalResults: Int?,
    @field:SerializedName("articles")
    val articles: List<Article>?
) : Parcelable


@Parcelize
data class ArticleResponse(
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("success")
    val success: Boolean?,
    @field:SerializedName("listArticles")
    val listArticles: List<Article>?
) : Parcelable