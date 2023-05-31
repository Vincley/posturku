package com.capstone.posturku.ui.news

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.posturku.data.repository.NewsRepository
import com.capstone.posturku.model.news.NewsResponse
import com.capstone.posturku.model.news.Resource
import com.capstone.posturku.model.news.entities.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _news: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val news: LiveData<Resource<List<Article>>> = _news
    private var allNewsDisplayed = mutableListOf<Article>()

    fun getNews(context: Context, category: String, pageNo: Int, isNew: Boolean) = viewModelScope.launch {
        _news.postValue(Resource.Loading())

        //checking if it is a new call for news or a paginated call
        if (isNew) {
            //if call is new then clearing the previous paginated news
            allNewsDisplayed.clear()
        }
        repository.getNews(context, category, pageNo)
            .onStart {
                Resource.Loading<NewsResponse>()
            }
            .flowOn(Dispatchers.IO)
            .catch {
                _news.postValue(Resource.Error("No internet connection"))
            }
            .collect {
//                Log.d("TAGYOYO", "collect ${it.data} ")
                if (it.data?.articles != null) {

                    //storing the response received from api for pagination
                    allNewsDisplayed.addAll(it.data.articles)
                    _news.postValue(Resource.Success(allNewsDisplayed.toList()))
                } else {
                    _news.postValue(Resource.Error("No internet connection"))
                }
            }
    }

}
