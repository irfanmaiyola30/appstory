package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository.Repo_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.RegisterResponse
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di.di_Event
import kotlinx.coroutines.launch

class Register_Model (private val repository: Repo_Story) : ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val registerResponse: LiveData<RegisterResponse> = repository.registerResponse
    val toastText: LiveData<di_Event<String>> = repository.toastText

    fun postRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.postRegister(name, email, password)
        }
    }
}