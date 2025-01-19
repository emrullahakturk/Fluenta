package com.yargisoft.fluenta.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class, OxfordWord::class], version = 1, exportSchema = false)
abstract class FluentaDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun oxfordWordDao(): OxfordWordDao
}
