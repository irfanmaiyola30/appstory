package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.data.RetroApiService
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.ListStoryItem
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_Session
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class StoryController (
    private val dataBase: StoryDb,
    private val sessionPreferences: Pref_Session,
    private val apiService: RetroApiService
) : RemoteMediator<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
                val controllerKeys = getRemoteKeyClosestToCurrentPosition(state)
                controllerKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        val token = sessionPreferences.getSession().first().token
        try {

            val responseData = apiService.getStoryListAppUserStory("Bearer $token", page, state.config.pageSize)

            val endOfPaginationReached = responseData.listStory.isEmpty()

            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dataBase.controllerKeysDao().deletedController()
                    dataBase.storyAppDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.listStory.map {
                    ControllerKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                dataBase.controllerKeysDao().insertAllStoryApp(keys)
                dataBase.storyAppDao().insertStory(responseData.listStory)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListStoryItem>): ControllerKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            dataBase.controllerKeysDao().getControllerKeyId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListStoryItem>): ControllerKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            dataBase.controllerKeysDao().getControllerKeyId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListStoryItem>): ControllerKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                dataBase.controllerKeysDao().getControllerKeyId(id)
            }
        }
    }
}