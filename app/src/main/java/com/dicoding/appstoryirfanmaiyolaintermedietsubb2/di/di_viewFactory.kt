package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository.Repo_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewadd.Add_StoryModel
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewLogin.Login_Model
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.ViewModel.viewMain.Main_Model
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.register.Register_Model

class di_viewVectory(private val repository: Repo_Story) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(Main_Model::class.java) -> {
                Main_Model(repository) as T
            }
            modelClass.isAssignableFrom(Register_Model::class.java) -> {
                Register_Model(repository) as T
            }
            modelClass.isAssignableFrom(Login_Model::class.java) -> {
                Login_Model(repository) as T
            }
            modelClass.isAssignableFrom(Add_StoryModel::class.java) -> {
                Add_StoryModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: di_viewVectory? = null
        fun getInstance(context: Context): di_viewVectory {
            return instance ?: synchronized(this) {
                instance ?: di_viewVectory(diinjection.provideRepository(context))
            }.also { instance = it }
        }
    }
}