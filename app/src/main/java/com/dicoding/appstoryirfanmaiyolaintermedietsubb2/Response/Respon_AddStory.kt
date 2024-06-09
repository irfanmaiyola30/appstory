package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response

import com.google.gson.annotations.SerializedName

data class Respon_AddStory(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
