package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_ModelSesion
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository.Repo_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.ListStoryItem
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.Respon_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di.di_Event
import kotlinx.coroutines.launch

class Main_Model(private val repository: Repo_Story) : ViewModel() {
    val list: LiveData<Respon_Story> = repository.list
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<di_Event<String>> = repository.toastText
    val getListStories: LiveData<PagingData<ListStoryItem>> = repository.getStories().cachedIn(viewModelScope)

    fun getSession(): LiveData<Pref_ModelSesion> {
        return repository.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getListStoriesWithLocation(token: String) {
        viewModelScope.launch {
            repository.getListStoriesWithLocation(token)
        }
    }
}
