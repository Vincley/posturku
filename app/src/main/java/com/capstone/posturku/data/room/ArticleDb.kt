package com.capstone.posturku.data.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ArticleDb")
@Parcelize
data class ArticleDb(
//    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,

//    @ColumnInfo(name = "idArticle")
//    var idArticle: Int? = 0,

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "url")
    var url: String = "",

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,

    @ColumnInfo(name = "publishedAt")
    var publishedAt: String? = null,

    @ColumnInfo(name = "content")
    var content: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "sourceName")
    var sourceName: String? = null
) : Parcelable