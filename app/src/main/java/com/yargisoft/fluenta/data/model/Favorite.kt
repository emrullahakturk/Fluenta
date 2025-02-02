package com.yargisoft.fluenta.data.model

import androidx.room.PrimaryKey

data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val meaning: String, // Anlamını saklamak için
    val category: String // "Oxford3000", "C1C2", "Phrases" vb. //
)