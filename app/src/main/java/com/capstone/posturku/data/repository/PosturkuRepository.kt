package com.capstone.posturku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.capstone.posturku.api.ApiConfig
import com.capstone.posturku.model.news.ArticleResponse
import com.capstone.posturku.model.news.entities.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PosturkuRepository() {

    fun GetAllArticles(keyword:String, callback: IListArticle) {
        val client = ApiConfig.getApiService().GetArticles(keyword)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>)
            {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.success==true) {
                        val value = responseBody?.listArticles
                        if(value != null){
                            callback.onSuccess(value!!)
                        }
                    }
                } else {
                    callback.onFailure("get data failed: ${response.code()}")
                    Log.e(PosturkuRepository.TAG, "onFailure - not success: ${response.isSuccessful}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                callback.onFailure("get data failed: ${t.message}")
                Log.e(PosturkuRepository.TAG, "onFailure - bad: ${t.message}")
            }
        })
    }

    interface IListArticle {
        fun onSuccess(listStory: List<Article>)
        fun onFailure(errorMessage: String)
    }

    companion object{
        private const val TAG = "AuthRepository"

        @Volatile
        private var INSTANCE: PosturkuRepository? = null

        fun getInstance(): PosturkuRepository {
            if (INSTANCE == null) {
                INSTANCE = PosturkuRepository()
            }
            return INSTANCE as PosturkuRepository
        }
    }
}