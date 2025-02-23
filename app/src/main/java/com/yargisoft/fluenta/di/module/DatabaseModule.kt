package com.yargisoft.fluenta.di.module

import android.content.Context
import androidx.room.Room
import com.yargisoft.fluenta.data.model.C1C2Dao
import com.yargisoft.fluenta.data.model.CommonPhraseDao
import com.yargisoft.fluenta.data.model.DatabaseInitializer
import com.yargisoft.fluenta.data.model.FavoriteDao
import com.yargisoft.fluenta.data.model.FluentaDatabase
import com.yargisoft.fluenta.data.model.MostCommonWordDao
import com.yargisoft.fluenta.data.model.OxfordWordDao
import com.yargisoft.fluenta.data.model.QuoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FluentaDatabase {
        return Room.databaseBuilder(
            context,
            FluentaDatabase::class.java,
            "fluenta_database"
        ).build()
    }

    @Provides
    fun provideQuoteDao(database: FluentaDatabase): QuoteDao {
        return database.quoteDao()
    }

    @Provides
    fun provideOxfordWordDao(database: FluentaDatabase): OxfordWordDao {
        return database.oxfordWordDao()
    }

    @Provides
    fun provideMostCommonWordDao(database: FluentaDatabase): MostCommonWordDao {
        return database.mostCommonWordDao()
    }

    @Provides
    fun provideCommonPhraseDao(database: FluentaDatabase): CommonPhraseDao {
        return database.commonPhraseDao()
    }

    @Provides
    fun provideFavoriteWordDao(database: FluentaDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun provideFavoriteC1C2Dao(database: FluentaDatabase): C1C2Dao {
        return database.c1C2Dao()
    }

    @Provides
    @Singleton
    fun provideDatabaseInitializer(
        @ApplicationContext context: Context,
        quoteDao: QuoteDao,
        mostCommonWordDao: MostCommonWordDao,
        oxfordWordDao: OxfordWordDao,
        commonPhraseDao: CommonPhraseDao,
        c1C2Dao: C1C2Dao
    ): DatabaseInitializer {
        return DatabaseInitializer(
            context,
            quoteDao,
            oxfordWordDao,
            mostCommonWordDao,
            commonPhraseDao,
            c1C2Dao
        )
    }

}
