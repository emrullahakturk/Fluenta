package com.yargisoft.fluenta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "common_phrases")
data class CommonPhrase(@PrimaryKey(autoGenerate = true)
                      val id: Int = 0,
                      val phrase: String,
                      val meaning: String,
                      val level: String,
                      val enExample: String,
                      val trExample: String)
