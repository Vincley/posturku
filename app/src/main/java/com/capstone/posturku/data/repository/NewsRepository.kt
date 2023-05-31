package com.capstone.posturku.data.repository

import android.content.Context
import com.capstone.posturku.api.news.ApiConfigNews
import com.capstone.posturku.api.news.ApiServiceNews
import com.capstone.posturku.model.news.NewsResponse
import com.capstone.posturku.model.news.Resource
import com.capstone.posturku.utils.news.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository {

    fun getNews(context: Context, category: String, pageNo: Int): Flow<Resource<NewsResponse>> {
        return flow {
            if (NetworkUtils.getNetworkStatus(context)) {
                val response = ApiConfigNews.getApiService().getTopHeadlines(category, pageNo)
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.articles != null) {
                        if (pageNo == 1)
                            //newsDAO.deleteArticlesFor(category)
                        body.articles.forEach {
                            it.category = category
                            //newsDAO.addArticle(it)
                        }
                        //saving total articles in response to datastore
                        //DataStoreManager(context).saveTotalArticles(body.totalResults!!)
                        emit(Resource.Success(body))
                    } else {
                        emit(Resource.Error("No response from server"))
                    }
                } else {
                    emit(Resource.Error("Server Error"))
                }
            } else {
                emit(Resource.Error("No internet connection"))
                //val articlesFlow = newsDAO.getAllArticlesUsingCategory(category)
//                articlesFlow.collect {
//                    if (it.isNullOrEmpty()) {
//                        emit(Resource.Error("No internet connection"))
//                    } else {
//                        emit(Resource.Success(NewsResponse("ok", it.size, it)))
//                    }
//                }
            }
        }
    }
    companion object{
        private const val TAG = "NewsRepository"

        @Volatile
        private var INSTANCE: NewsRepository? = null

        fun getInstance(): NewsRepository {
            if (INSTANCE == null) {
                INSTANCE = NewsRepository()
            }
            return INSTANCE as NewsRepository
        }
    }
}