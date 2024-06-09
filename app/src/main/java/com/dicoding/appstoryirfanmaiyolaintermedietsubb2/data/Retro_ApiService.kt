package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.data

import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.Respon_AddStory
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.Respon_Login
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.RegisterResponse
import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.Respon_Story
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Query

interface RetroApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegisterUserAppStory(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLoginUserAppStory(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Respon_Login>

    @GET("stories")
    suspend fun getStoryListAppUserStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): Respon_Story

    @GET("stories")
    fun getLocationStoryAppUser(
        @Header("Authorization") token: String,
        @Query("location") loc: Int = 1
    ): Call<Respon_Story>

    @Multipart
    @POST("stories")
    suspend fun postStoryAppStoryUser(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Respon_AddStory
}