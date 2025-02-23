package com.yargisoft.fluenta.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
data class Story(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val story: String,
    val level: String
)
