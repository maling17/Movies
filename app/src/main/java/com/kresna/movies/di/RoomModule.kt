package com.kresna.movies.di

import android.content.Context
import androidx.room.Room
import com.kresna.movies.db.MovieDatabase
import com.kresna.movies.db.MovieDatabase.Companion.DATABASE_NAME
import com.kresna.movies.db.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun ProvidesMoviesDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(movieDatabase: MovieDatabase): MoviesDao {
        return movieDatabase.moviesDao()
    }
}