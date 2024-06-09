package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_Session
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository.Repo_Story
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.data.RetroConfigApi
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.db.StoryDb
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object diinjection {
    fun provideRepository(context: Context): Repo_Story {
        val dataBase = StoryDb.getDatabase(context)
        val pref = Pref_Session.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = RetroConfigApi.getApiService(user.token)
        return Repo_Story.getInstance(dataBase, pref, apiService)
    }
}