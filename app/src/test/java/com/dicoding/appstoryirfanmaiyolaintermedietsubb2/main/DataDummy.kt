package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.main

import com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.ListStoryItem

object DataDummy {
    fun generateDummyStoryEntity(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()
        for (i in 1..10) {
            val news = ListStoryItem(
                id = "story-FvU4u0Vp2S3PMsFg",
                name = "Dimas",
                description = "Lorem Ipsum",
                photo = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                lat = -10.212,
                lon = -16.002,
            )
            storyList.add(news)
        }
        return storyList
    }
}