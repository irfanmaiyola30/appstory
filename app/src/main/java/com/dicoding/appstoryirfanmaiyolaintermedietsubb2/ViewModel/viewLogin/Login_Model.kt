package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.Respon_Login
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository.Repo_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di.di_Event
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_ModelSesion
import kotlinx.coroutines.launch

class Login_Model(private val repository: Repo_Story) : ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val loginResponse: LiveData<Respon_Login> = repository.loginResponse
    val toastText: LiveData<di_Event<String>> = repository.toastText

    fun postLoginUserApp(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            repository.postLogin(emailUser, passwordUser)
        }
    }

    fun saveSession(session: Pref_ModelSesion) {
        viewModelScope.launch {
            repository.saveSession(session)
        }
    }

}