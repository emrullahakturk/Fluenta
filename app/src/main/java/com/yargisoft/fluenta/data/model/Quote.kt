package com.yargisoft.fluenta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quotes")
data class Quote(  @PrimaryKey(autoGenerate = true)
                   val id: Int = 0,
                   val author: String,
                   val quote: String){

}
