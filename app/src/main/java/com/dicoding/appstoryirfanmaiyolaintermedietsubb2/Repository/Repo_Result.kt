package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Repository

sealed class RepoResult<out R> private constructor() {
    data class Success<out T>(val data: T) : RepoResult<T>()
    data class Error(val error: String) : RepoResult<Nothing>()
    object Loading : RepoResult<Nothing>()
}
