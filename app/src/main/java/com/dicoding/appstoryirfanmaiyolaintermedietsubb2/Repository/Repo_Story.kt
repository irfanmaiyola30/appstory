package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.data.RetroApiService
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.*
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.db.StoryController
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di.di_Event
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_ModelSesion
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.prefrence.Pref_Session
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.db.StoryDb
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class Repo_Story private constructor(
    private val dataBase: StoryDb,
    private val pref: Pref_Session,
    private val apiService: RetroApiService
) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<Respon_Login>()
    val loginResponse: LiveData<Respon_Login> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _list = MutableLiveData<Respon_Story>()
    val list: LiveData<Respon_Story> = _list

    private val _toastText = MutableLiveData<di_Event<String>>()
    val toastText: LiveData<di_Event<String>> = _toastText

    fun postRegister(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = apiService.postRegisterUserAppStory(name, email, password)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _registerResponse.value = response.body()
                    _toastText.value = di_Event(response.body()?.message.toString())
                } else {
                    _toastText.value = di_Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _toastText.value = di_Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.postLoginUserAppStory(email, password)

        client.enqueue(object : Callback<Respon_Login> {
            override fun onResponse(
                call: Call<Respon_Login>,
                response: Response<Respon_Login>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                    _toastText.value = di_Event(response.body()?.message.toString())
                } else {
                    _toastText.value = di_Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<Respon_Login>, t: Throwable) {
                _toastText.value = di_Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryController(dataBase, pref, apiService),
            pagingSourceFactory = {
                dataBase.storyAppDao().getAllStory()
            }
        ).liveData
    }


    fun getListStoriesWithLocation(token: String) {
        _isLoading.value = true
        val client = apiService.getLocationStoryAppUser("Bearer $token")

        client.enqueue(object : Callback<Respon_Story> {
            override fun onResponse(
                call: Call<Respon_Story>,
                response: Response<Respon_Story>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _list.value = response.body()
                } else {
                    _toastText.value = di_Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<Respon_Story>, t: Throwable) {
                _toastText.value = di_Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    fun uploadStories(token: String, imageFile: File, description: String, lat: Double?, lon: Double?) = liveData {
        emit(RepoResult.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        val requestLat = lat?.toString()?.toRequestBody()
        val requestLon = lon?.toString()?.toRequestBody()
        try {
            val successResponse = apiService.postStoryAppStoryUser("Bearer $token", multipartBody, requestBody, requestLat, requestLon)
            if (successResponse.error) {
                emit(RepoResult.Error(successResponse.message))
            } else {
                emit(RepoResult.Success(successResponse))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, Respon_Eror::class.java)
            val errorMessage = errorBody.message
            emit(RepoResult.Error(errorMessage.toString()))
        }
    }

    fun getSession(): LiveData<Pref_ModelSesion> {
        return pref.getSession().asLiveData()
    }

    suspend fun saveSession(session: Pref_ModelSesion) {
        pref.saveSession(session)
    }

    suspend fun logout() {
        pref.logout()
    }

    fun getQuote(auth: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                QuotePagingSource(apiService, auth = auth)
            }
        ).liveData
    }

    companion object {
        private const val TAG = "StoryRepository"
        @Volatile
        private var instance: Repo_Story? = null

        fun getInstance(
            dataBase: StoryDb,
            preferences: Pref_Session,
            apiService: RetroApiService
        ): Repo_Story =
            instance ?: synchronized(this) {
                instance ?: Repo_Story(dataBase, preferences, apiService)
            }.also { instance = it }
    }
}