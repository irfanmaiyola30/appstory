package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response

import com.google.gson.annotations.SerializedName

data class Respon_Login(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null
)

data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)
