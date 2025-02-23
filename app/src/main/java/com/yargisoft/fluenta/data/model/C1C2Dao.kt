package com.yargisoft.fluenta.data.model


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface C1C2Dao {
    @Query("SELECT * FROM c1_c2_stories ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomC1C2Story(): C1C2Story

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(c1C2Story: List<C1C2Story>)
}
