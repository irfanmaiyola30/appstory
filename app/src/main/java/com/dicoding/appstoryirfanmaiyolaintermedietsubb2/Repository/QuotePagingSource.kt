package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.ListStoryItem
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.Respon_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.data.RetroApiService

class QuotePagingSource(private val apiService: RetroApiService,private val auth:String) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStoryListAppUserStory("Bearer $auth", page = page, size = params.loadSize)

            Log.d("TAG", "load: $responseData")
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.d("TAG", "load: ${exception.message}")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}