package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import  com.dicoding.appstoryirfanmaiyolaintermedietsubb2.Response.ListStoryItem

@Database(
    entities = [ListStoryItem::class, ControllerKey::class],
    version = 3,
    exportSchema = false
)
abstract class StoryDb : RoomDatabase() {

    abstract fun storyAppDao() : StoryDao
    abstract fun controllerKeysDao(): ControllerKeyDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDb? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDb::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}