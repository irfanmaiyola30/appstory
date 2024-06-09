package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.di

open class di_Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun consumeContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
