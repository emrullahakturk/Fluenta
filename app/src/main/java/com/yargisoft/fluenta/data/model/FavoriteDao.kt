package com.yargisoft.fluenta.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteWord(favoriteWord: Favorite)

    @Query("DELETE FROM favorites WHERE word = :word AND category = :category")
    suspend fun deleteFavoriteWord(word: String, category: String)

    @Query("SELECT * FROM favorites WHERE category = :category")
    fun getFavoritesByCategory(category: String): Flow<List<Favorite>>

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE word = :word AND category = :category)")
    suspend fun isWordFavorite(word: String, category: String): Boolean
}
