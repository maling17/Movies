package com.kresna.movies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kresna.movies.model.ResultsItem


@Database(entities = [ResultsItem::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao


    companion object {
        val DATABASE_NAME :String = "movie_db.db"
    }


}