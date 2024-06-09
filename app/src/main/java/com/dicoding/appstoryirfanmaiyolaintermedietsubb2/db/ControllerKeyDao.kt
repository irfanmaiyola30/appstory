package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ControllerKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStoryApp(controllerKey: List<ControllerKey>)

    @Query("SELECT * FROM Controller_key WHERE id = :id")
    suspend fun getControllerKeyId(id: String): ControllerKey?

    @Query("DELETE FROM Controller_key")
    suspend fun deletedController()
}