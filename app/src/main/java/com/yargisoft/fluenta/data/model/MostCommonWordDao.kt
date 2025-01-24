package com.yargisoft.fluenta.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MostCommonWordDao {
    @Query("SELECT * FROM most_common_c1_c2 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomMostCommonWord(): MostCommonWord

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mostCommonWords: List<MostCommonWord>)
}
