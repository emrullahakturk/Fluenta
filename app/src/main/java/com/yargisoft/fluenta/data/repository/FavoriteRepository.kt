package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.model.Favorite
import com.yargisoft.fluenta.data.model.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    suspend fun addFavorite(word: String, category: String, level: String, meaning: String, trExample: String, enExample: String) {
        favoriteDao.insertFavoriteWord(
            Favorite(
                word = word,
                category = category,
                level = level,
                meaning = meaning,
                trExample = trExample,
                enExample = enExample
            )
        )
    }

    suspend fun removeFavorite(word: String, category: String) {
        val isFavorite = favoriteDao.isWordFavorite(word, category)
        if (isFavorite) {
            favoriteDao.deleteFavoriteWord(word, category)
        }
    }

    fun getFavoritesByCategory(category: String): Flow<List<Favorite>> {
        return favoriteDao.getFavoritesByCategory(category)
    }

    suspend fun isWordFavorite(word: String, category: String): Boolean {
        return favoriteDao.isWordFavorite(word, category)
    }
}
