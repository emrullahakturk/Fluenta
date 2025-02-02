package com.yargisoft.fluenta.data.repository

import android.util.Log
import android.widget.Toast
import com.yargisoft.fluenta.data.model.Favorite
import com.yargisoft.fluenta.data.model.FavoriteWordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteWordDao: FavoriteWordDao
) {
    suspend fun addFavorite(word: String, category: String) {
        favoriteWordDao.insertFavoriteWord(Favorite(word = word, category = category))
        Log.d("FavoriteRepository", "Favorite word added: $word")
    }

    suspend fun removeFavorite(word: String, category: String) {
        val isFavorite = favoriteWordDao.isWordFavorite(word, category)
        if (isFavorite) {
            favoriteWordDao.deleteFavoriteWord(word, category)
            Log.d("FavoriteRepository", "Favorite word removed: $word")
        }
    }

    fun getFavoritesByCategory(category: String): Flow<List<Favorite>> {
        return favoriteWordDao.getFavoritesByCategory(category)
    }

    suspend fun isWordFavorite(word: String, category: String): Boolean {
        return favoriteWordDao.isWordFavorite(word, category)
    }
}
