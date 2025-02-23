package com.yargisoft.fluenta.data.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class, OxfordWord::class,MostCommonWord::class, CommonPhrase::class,Favorite::class,C1C2Story::class], version = 1, exportSchema = false)
abstract class FluentaDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun oxfordWordDao(): OxfordWordDao
    abstract fun mostCommonWordDao(): MostCommonWordDao
    abstract fun commonPhraseDao(): CommonPhraseDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun c1C2Dao(): C1C2Dao
}
