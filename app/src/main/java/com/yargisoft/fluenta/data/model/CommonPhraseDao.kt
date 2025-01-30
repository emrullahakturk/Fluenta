package com.yargisoft.fluenta.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommonPhraseDao {
    @Query("SELECT * FROM oxford_words ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCommonPhrase(): CommonPhrase

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(commonPhrase: List<CommonPhrase>)
}
