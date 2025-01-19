package com.yargisoft.fluenta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "oxford_words")
data class OxfordWord(@PrimaryKey(autoGenerate = true)
                       val id: Int = 0,
                      val word: String,
                      val type: String,
                      val meaning: String,
                      val level: String,
                      val enExample: String,
                      val trExample: String)
