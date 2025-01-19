package com.yargisoft.fluenta.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface OxfordWordDao {
    @Query("SELECT * FROM oxford_words ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomOxfordWord(): OxfordWord

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(oxfordWords: List<OxfordWord>)
}
