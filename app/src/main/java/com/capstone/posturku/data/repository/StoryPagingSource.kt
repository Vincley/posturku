package com.capstone.posturku.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.posturku.api.ApiConfig
import com.capstone.posturku.model.news.entities.Article

class StoryPagingSource(private val token: String) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = ApiConfig.getApiService().GetAllStories(token, page, params.loadSize)

//            val responseData = response.listStory

//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
//            )

            //HAPUS INI NANTI
            LoadResult.Page(
                data = emptyList(),
                prevKey = 1,
                nextKey = 2
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}