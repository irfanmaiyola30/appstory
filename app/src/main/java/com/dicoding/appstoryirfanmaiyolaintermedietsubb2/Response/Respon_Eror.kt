package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response

import com.google.gson.annotations.SerializedName

data class Respon_Eror(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)