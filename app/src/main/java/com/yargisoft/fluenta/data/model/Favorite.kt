package com.yargisoft.fluenta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val category: String, // "Oxford3000", "C1C2", "Phrases" vb. //
    val level: String,
    val meaning: String,
    val trExample: String,
    val enExample: String
)