package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_ModelSesion
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository.Repo_Story
import java.io.File

class Add_StoryModel(private val repository: Repo_Story) : ViewModel() {

    fun getSession(): LiveData<Pref_ModelSesion> {
        return repository.getSession()
    }
    fun uploadStories(token: String, file: File, description: String, lat: Double? = null, lon: Double? = null) =
        repository.uploadStories(token, file, description, lat, lon)
}