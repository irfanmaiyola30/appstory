package com.dicoding.appstoryirfanmaiyolaintermedietsubb2.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Controller_key")
data class ControllerKey(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)