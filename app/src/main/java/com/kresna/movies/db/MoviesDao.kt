package com.kresna.movies.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kresna.movies.model.ResultsItem

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(resultsItem: ResultsItem)

    @Query("select*from movies")
    fun getAllMovies():LiveData<List<ResultsItem>>

    @Delete
    suspend fun deleteMovies(resultsItem: ResultsItem)

}