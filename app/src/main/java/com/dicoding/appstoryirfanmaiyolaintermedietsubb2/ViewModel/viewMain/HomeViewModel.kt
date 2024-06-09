package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository.Repo_Story
import kotlinx.coroutines.Job

class HomeViewModel constructor (private val dataRepository: Repo_Story): ViewModel() {

    fun getStory(auth: String) = dataRepository.getQuote(auth).cachedIn(viewModelScope)
    private var job: Job? = null
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}