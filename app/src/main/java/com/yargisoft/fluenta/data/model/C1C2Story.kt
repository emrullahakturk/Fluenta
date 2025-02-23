package com.yargisoft.fluenta.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "c1_c2_stories")
data class C1C2Story(@PrimaryKey(autoGenerate = true)
                      val id: Int = 0,
                     val title: String,
                     val story: String,
                     )
