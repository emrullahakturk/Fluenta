package com.yargisoft.fluenta.di.module

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.yargisoft.fluenta.data.model.DatabaseInitializer
import com.yargisoft.fluenta.data.model.FluentaDatabase
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
    @Singleton
    fun provideDatabaseInitializer(@ApplicationContext  context: Context, dao: QuoteDao): DatabaseInitializer {
        return DatabaseInitializer(context, dao)
    }

}
