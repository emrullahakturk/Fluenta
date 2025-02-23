package com.yargisoft.fluenta.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {

    @Query("SELECT * FROM stories ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomStory(): Story

    @Query("SELECT * FROM stories WHERE level = 'A1-A2' ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomStoryA1A2(): Story

    @Query("SELECT * FROM stories WHERE level = 'B1-B2' ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomStoryB1B2(): Story

    @Query("SELECT * FROM stories WHERE level = 'C1-C2' ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomStoryC1C2(): Story

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(story: List<Story>)
}
